package connectus.connectus;

import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class AsyncMapStep extends AsyncTask<Void, Void, Void> {
    private boolean checkboxEnabled;
    private int userId;
    private int mapPos;

    public AsyncMapStep(int userId, int mapPos, boolean checkboxEnabled){
        this.checkboxEnabled = checkboxEnabled;
        this.userId = userId;
        this.mapPos = mapPos;
    }

    @Override
    protected Void doInBackground(Void... args){
        HttpClient httpclient = new DefaultHttpClient();

        int newMapPos;
        if(checkboxEnabled){
            newMapPos = mapPos;
        }else{
            newMapPos = mapPos-1;
        }

//        try {
//            HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/changeMapPos.php?userId=" + userId + "&mapPos=" + newMapPos);
//            HttpResponse response = httpclient.execute(httprequest);
//        }catch (IOException e){
//            Log.e("Exception", "IOException");
//        }

        return null;
    }
}
