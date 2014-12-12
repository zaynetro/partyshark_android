package devshark.partyshark;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by zaynetro on 11.12.2014.
 */
public class SongsAdapter extends ArrayAdapter<Song> {

    Playlist playlist;

    public SongsAdapter(Context context, ArrayList<Song> songs, Playlist playlist) {
        super(context, R.layout.song_item, songs);
        this.playlist = playlist;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        // Get the data item for this position
        final Song song = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_item, parent, false);
        }
        // Lookup view for data population
        TextView text = (TextView) convertView.findViewById(R.id.songTitle);
        Button addBtn = (Button) convertView.findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddSong().execute(song);
            }
        });
        // Populate the data into the template view using the data object
        text.setText(song.title);
        // Return the completed view to render on screen
        return convertView;
    }

    /**
     * Task to download json and put to FeedFragment
     */
    private class AddSong extends AsyncTask<Song, Void, String> {
        @Override
        protected String doInBackground(Song... songs) {
            // params comes from the execute() call: params[0] is the url.
            return new Loader().addSong(songs[0], playlist);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), "Song was added", Toast.LENGTH_SHORT).show();
        }
    }

}
