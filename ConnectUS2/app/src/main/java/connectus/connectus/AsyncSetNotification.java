package connectus.connectus;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by egiurleo on 4/16/15.
 */
public class AsyncSetNotification extends AsyncTask<Void, Void, Void> {

    private String userId;
    private String notifications;
    private boolean accept;
    private String friends;

    public AsyncSetNotification(String userId, String notifications, boolean accept, String friends){
        this.userId = userId;
        this.notifications = notifications;
        this.accept = accept;
        this.friends = friends;
    }

    @Override
    protected Void doInBackground(Void... args){
        //i don't think i need to worry about connectivity in here
        HttpClient httpclient = new DefaultHttpClient();
        try {
            String urlString = "http://egiurleo.scripts.mit.edu/setNotifications.php?userId=" + userId + "&notifications=" + notifications;
            urlString = urlString.replace(" ", "+");

            HttpGet httprequest = new HttpGet(urlString);
            HttpResponse response = httpclient.execute(httprequest);
        }catch(IOException e){
            Log.e("Exception", "IOException");
        }

        if(accept) {
            try {
                String urlString = "http://egiurleo.scripts.mit.edu/setFriends.php?userId=" + userId + "&friends=" + friends;
                urlString = urlString.replace(" ", "+");

                HttpGet httprequest2 = new HttpGet(urlString);
                HttpResponse response2 = httpclient.execute(httprequest2);
            } catch (IOException e) {
                Log.e("Exception", "IOException");
            }
        }

        return null;
    }
}
