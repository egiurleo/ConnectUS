package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class AsyncMapStep extends AsyncTask<Void, Void, Void> {
    private boolean checkboxEnabled;
    private String userId;
    private int mapPos;
    private Context context;
    private Activity activity;
    private boolean connected;

    public AsyncMapStep(String userId, int mapPos, boolean checkboxEnabled, Context context, Activity activity){
        this.checkboxEnabled = checkboxEnabled;
        this.userId = userId;
        this.mapPos = mapPos;
        this.context = context;
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

            int newMapPos;
            if (checkboxEnabled) {
                newMapPos = mapPos;
            } else {
                if (mapPos == 6) {
                    newMapPos = mapPos - 2;
                } else {
                    newMapPos = mapPos - 1;
                }

            }

            try {
                HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/changeMapPos.php?userId=" + userId + "&mapPos=" + newMapPos);
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
        if(connected) {
            AsyncLogin asyncLogin = new AsyncLogin(context);
            asyncLogin.execute(userId);
        }
    }

}
