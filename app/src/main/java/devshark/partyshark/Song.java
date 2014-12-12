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
    public int playlist;
    public boolean isPlayed;
    public int soundCloudID;

    public Song(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public Song(JSONObject json) {
        try {
            this.title = json.getString("title");
            this.soundCloudID = json.getInt("id");
            this.playlist = json.getInt("playlist");
            this.isPlayed = json.getBoolean("isPlayed");
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

    public JSONObject getJSON(Playlist playlist) {
        JSONObject json = new JSONObject();

        try {
            json.put("soundCloudID", this.soundCloudID);
            json.put("songTitle", this.title);
            json.put("playlist", playlist.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

}
