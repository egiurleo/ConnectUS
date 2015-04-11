package connectus.connectus;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by egiurleo on 4/11/15.
 */
public class Test extends AsyncTask <Void, Void, String> {

    @Override
    protected String doInBackground(Void... args){
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://egiurleo.scripts.mit.edu/helloworld.php");

        try{
            HttpResponse response = httpclient.execute(httpget);

            if(response != null){
                String line = "";
                InputStream inputStream = response.getEntity().getContent();
                return convertStreamToString(inputStream);

            }else{
                return "response is null...";
            }
        } catch (IOException e){
            return "sad face...";
        }
    }

    @Override
    protected void onPostExecute(String result){
        System.out.println(result);
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
