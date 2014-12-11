package devshark.partyshark;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Search extends Activity {

    private EditText searchField;
    private Button searchBtn;
    private ListView foundList;
    private SongsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = (EditText) findViewById(R.id.searchField);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        foundList = (ListView) findViewById(R.id.foundList);

        // Construct the data source
        ArrayList<Song> songs = new ArrayList<Song>();
        // Create the adapter to convert the array to views
        adapter = new SongsAdapter(this, songs);
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


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
