package devshark.partyshark;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class Playlists extends Activity {

    private ListView playlistsField;
    private PlaylistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        playlistsField = (ListView) findViewById(R.id.playlists);

        // Construct the data source
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        // Create the adapter to convert the array to views
        adapter = new PlaylistAdapter(this, playlists);
        // Attach the adapter to a ListView
        playlistsField.setAdapter(adapter);

        playlistsField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Playlist playlist = (Playlist) parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), Search.class);
                intent.putExtra("Playlist", playlist);

                startActivity(intent);
            }
        });

        new LoadPlaylists().execute();
    }

    /**
     * Task to download json and put to FeedFragment
     */
    private class LoadPlaylists extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return new Loader().getPlaylists();
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray json = new JSONArray(result);
                adapter.clear();
                adapter.addAll(Playlist.fromJSON(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
