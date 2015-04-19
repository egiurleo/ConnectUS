package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by egiurleo on 4/17/15.
 */
public class AsyncSendFriendRequest extends AsyncTask<Void, Void, Void> {

    private String userId;
    private String notificationId;
    private Context context;
    private Activity activity;
    private boolean connected;

    public AsyncSendFriendRequest(String notificationId, Context context, Activity activity){
        this.context = context;
        this.notificationId = notificationId;
        this.activity = activity;
        connected = true;
    }

    @Override
    protected Void doInBackground(Void... args){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            HttpClient httpclient = new DefaultHttpClient();

            String returnString = "";

            try {

                //get the stored file
                FileInputStream fileinput = context.openFileInput("connectus_user_info");
                Scanner scanner = new Scanner(fileinput);

                while (scanner.hasNext()) {
                    returnString = scanner.nextLine();
                }

                String[] userInfo = returnString.split("\\|");
                userId = userInfo[0];
            } catch (IOException e) {
                Log.e("Error: ", "file not found");
            }

            try {
                String urlString = "http://egiurleo.scripts.mit.edu/addNotification.php?userId=" + notificationId + "&notification=" + userId;

                HttpGet httprequest = new HttpGet(urlString);
                HttpResponse response = httpclient.execute(httprequest);
            } catch (IOException e) {
                Log.e("Exception", "IOException");
            }
        }else{
            activity.findViewById(R.id.network_warning).setVisibility(View.VISIBLE);
            connected = false;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        if(connected){
            Button friendRequestButton = (Button) activity.findViewById(R.id.send_friend_request);
            friendRequestButton.setVisibility(View.GONE);

            TextView friendRequestSent = (TextView) activity.findViewById(R.id.friend_request_sent);
            friendRequestSent.setVisibility(View.VISIBLE);
        }

    }
}
