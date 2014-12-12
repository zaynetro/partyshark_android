package devshark.partyshark;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zaynetro on 11.12.2014.
 */
public class Loader {
    Context context;
    private static final String DEBUG_TAG = "Loader";

    private static String Client_ID = "b365d76bf1520df0559a12bd6fee5519";

    public Loader(Context context) {
        this.context = context;
    }

    public Loader() {}

    public String getSongs(String search) {
        String url = "https://api.soundcloud.com/tracks" +
                "?client_id=" + this.Client_ID +
                "&q=" + search;
        return getJSON(url);
    }

    public String getPlaylists() {
        String url = "http://172.16.0.127:8000/api/playlists/";
        return getJSON(url);
    }

    public String addSong(Song song, Playlist playlist) {
        String url = "http://172.16.0.127:8000/api/songs/";
        return postJSON(url, song.getJSON(playlist));
    }

    public String loadSkenes(JSONObject params) {
        String url = "https://api.parse.com/1/functions/get";
        return postJSON(url, params);
    }

    /**
     * General method to POST json's
     */
    private String postJSON(String url, JSONObject json) {
        return this.request(url, json, "POST");
    }

    private String getJSON(String url) {
        return this.request(url, null, "GET");
    }

    private String request(String url, JSONObject json, String method) {
        String resultString = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpResponse response;
            if(method == "GET") {
                HttpGet httpRequest = new HttpGet(url);
                response = (HttpResponse) httpClient.execute(httpRequest);
            } else if (method == "POST") {
                HttpPost httpRequest = new HttpPost(url);
                StringEntity se = new StringEntity(json.toString());
                // Set HTTP parameters
                httpRequest.setEntity(se);
                httpRequest.setHeader("Content-Type", "application/json");
                response = (HttpResponse) httpClient.execute(httpRequest);
            } else {
                return resultString;
            }

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Read the content stream
                InputStream inStream = entity.getContent();

                // convert content stream to a String
                resultString = convertStreamToString(inStream);
                inStream.close();

                Log.i(DEBUG_TAG,"Result: " + resultString);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return resultString;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
