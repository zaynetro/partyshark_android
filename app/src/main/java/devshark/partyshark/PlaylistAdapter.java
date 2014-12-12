package devshark.partyshark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zaynetro on 12.12.2014.
 */
public class PlaylistAdapter extends ArrayAdapter<Playlist> {

    public PlaylistAdapter(Context context, ArrayList<Playlist> playlists) {
        super(context, android.R.layout.simple_list_item_1, playlists);
       }

@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Playlist playlist = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        // Lookup view for data population
        TextView text = (TextView) convertView.findViewById(android.R.id.text1);
        // Populate the data into the template view using the data object
        text.setText(playlist.name);
        // Return the completed view to render on screen
        return convertView;
    }
}
