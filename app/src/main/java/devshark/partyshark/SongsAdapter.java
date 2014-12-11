package devshark.partyshark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zaynetro on 11.12.2014.
 */
public class SongsAdapter extends ArrayAdapter<Song> {

    public SongsAdapter(Context context, ArrayList<Song> songs) {
        super(context, R.layout.song_item, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Song song = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_item, parent, false);
        }
        // Lookup view for data population
        TextView text = (TextView) convertView.findViewById(R.id.songTitle);
        Button addBtn = (Button) convertView.findViewById(R.id.addBtn);
        // Populate the data into the template view using the data object
        text.setText(song.title);
        // Return the completed view to render on screen
        return convertView;
    }

}
