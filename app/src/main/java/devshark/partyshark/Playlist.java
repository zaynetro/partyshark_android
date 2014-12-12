package devshark.partyshark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zaynetro on 12.12.2014.
 */
public class Playlist implements Serializable {

    public String name;
    public int id;

    public Playlist(JSONObject json) {
        try {
            this.name = json.getString("name");
            this.id = json.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Playlist> fromJSON(JSONArray json) {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();

        for(int i = 0; i < json.length(); i++) {
            try {
                playlists.add(new Playlist(json.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return playlists;
    }
}
