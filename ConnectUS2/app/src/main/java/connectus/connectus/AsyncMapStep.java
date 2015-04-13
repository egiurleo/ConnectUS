package connectus.connectus;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

    public AsyncMapStep(String userId, int mapPos, boolean checkboxEnabled, Context context){
        this.checkboxEnabled = checkboxEnabled;
        this.userId = userId;
        this.mapPos = mapPos;
        this.context = context;

        System.out.println(checkboxEnabled + ", " + userId + ", " + mapPos);
    }

    @Override
    protected Void doInBackground(Void... args){
        HttpClient httpclient = new DefaultHttpClient();

        int newMapPos;
        if(checkboxEnabled){
            newMapPos = mapPos;
        }else{
            if(mapPos == 6){
                newMapPos = mapPos - 2;
            }else{
                newMapPos = mapPos-1;
            }

        }

        try {
            System.out.println("Http request!");
            HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/changeMapPos.php?userId=" + userId + "&mapPos=" + newMapPos);
            HttpResponse response = httpclient.execute(httprequest);
        }catch (IOException e){
            Log.e("Exception", "IOException");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        AsyncLogin asyncLogin = new AsyncLogin(context);
        asyncLogin.execute(userId);
    }

}
