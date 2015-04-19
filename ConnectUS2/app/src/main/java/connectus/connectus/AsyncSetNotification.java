package connectus.connectus;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by egiurleo on 4/16/15.
 */
public class AsyncSetNotification extends AsyncTask<Void, Void, Void> {

    private String userId;
    private String notifications;
    private boolean accept;
    private String friends;
    private String friendId;

    public AsyncSetNotification(String userId, String notifications, boolean accept, String friends, String friendId){
        this.userId = userId;
        this.notifications = notifications;
        this.accept = accept;
        this.friends = friends;
        this.friendId = friendId;
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

            //do it for the friend too
            String friendsOfFriend = "";

            try {
                String urlString3 = "http://egiurleo.scripts.mit.edu/getFriends.php?userId=" + friendId;
                HttpGet httprequest3 = new HttpGet(urlString3);
                HttpResponse response3 = httpclient.execute(httprequest3);

                if (response3 != null) {
                    InputStream inputStream3 = response3.getEntity().getContent();

                    //return the string to be cached
                    friendsOfFriend = convertStreamToString(inputStream3);
                }
            } catch (IOException e) {
                Log.e("Exception", "IOException");
            }

            friendsOfFriend = friendsOfFriend + " " + userId;

            try {
                String urlString4 = "http://egiurleo.scripts.mit.edu/setFriends.php?userId=" + friendId + "&friends=" + friendsOfFriend;
                urlString4 = urlString4.replace(" ", "+");

                HttpGet httprequest4 = new HttpGet(urlString4);
                HttpResponse response4 = httpclient.execute(httprequest4);
            } catch (IOException e) {
                Log.e("Exception", "IOException");
            }


        }

        return null;
    }

    private String convertStreamToString(InputStream is){
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (Exception e) {
            return "Problem with string";
        }
        return total.toString();
    }
}
