package devshark.partyshark;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class Search extends Activity {

    private EditText searchField;
    private Button searchBtn;
    private ListView foundList;
    private SongsAdapter adapter;
    private Playlist playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = (EditText) findViewById(R.id.searchField);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        foundList = (ListView) findViewById(R.id.foundList);

        Intent intent = getIntent();
        playlist = (Playlist) intent.getSerializableExtra("Playlist");

        if(playlist == null) {
            // Show error banner and go back
        }

        setTitle(playlist.name);

        // Construct the data source
        ArrayList<Song> songs = new ArrayList<Song>();
        // Create the adapter to convert the array to views
        adapter = new SongsAdapter(this, songs, playlist);
        // Attach the adapter to a ListView
        foundList.setAdapter(adapter);
    }

    public void searchSongs(View view) {
        String search = searchField.getText().toString();
        new FindSongs().execute(search);
    }

    /**
     * Task to download json and put to FeedFragment
     */
    private class FindSongs extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url.
            return new Loader().getSongs(params[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray json = new JSONArray(result);
                adapter.clear();
                adapter.addAll(Song.fromJSON(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
