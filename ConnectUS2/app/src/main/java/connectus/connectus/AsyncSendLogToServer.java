package connectus.connectus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by egiurleo on 4/18/15.
 */
public class AsyncSendLogToServer extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String id;

    public AsyncSendLogToServer(Context context, String id){
        this.context = context;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... args){

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            String ret = "";

            try {
                File sdCard = Environment.getExternalStorageDirectory();
                FileInputStream inputStream = new FileInputStream(sdCard.getAbsolutePath() + "/myLogcat/logcat.txt");

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();

                    HttpClient httpclient = new DefaultHttpClient();

                    ret = ret.replace("'", "squote");
                    String urlString = "http://egiurleo.scripts.mit.edu/writeLogFiles.php?userId=" + id + "&logs=" + URLEncoder.encode(ret);
                    HttpGet httprequest = new HttpGet(urlString);
                    HttpResponse response = httpclient.execute(httprequest);
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            }

        }

        return null;

    }
}
