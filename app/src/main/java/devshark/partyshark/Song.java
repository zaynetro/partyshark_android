package devshark.partyshark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zaynetro on 11.12.2014.
 */
public class Song {
    public String title;
    public int id;

    public Song(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public Song(JSONObject json) {
        try {
            this.title = json.getString("title");
            this.id = json.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Song> fromJSON(JSONArray json) {
        ArrayList<Song> songs = new ArrayList<Song>();

        for(int i = 0; i < json.length(); i++) {
            try {
                songs.add(new Song(json.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return songs;
    }


}
