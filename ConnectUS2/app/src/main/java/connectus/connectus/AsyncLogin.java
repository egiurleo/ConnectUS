package connectus.connectus;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by egiurleo on 4/11/15.
 */
public class AsyncLogin extends AsyncTask <String, Void, String> {

    private Context context;

    public AsyncLogin(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... args){
        HttpClient httpclient = new DefaultHttpClient();

        String id = args[0];
        String name = args[1];

        //if this username exists...
        HttpGet httpUserExists = new HttpGet("http://egiurleo.scripts.mit.edu/checkUserExists.php?userId=" + id);

        try{
            HttpResponse response = httpclient.execute(httpUserExists);

            if(response != null){
                InputStream inputStream = response.getEntity().getContent();

                //if this user doesn't exist, put their information on the db
                if(convertStreamToString(inputStream) == "false"){
                    HttpGet httpRegister = new HttpGet("http://jbeau16.scripts.mit.edu/register.php?userId=" + id + "&name=" + name);
                    //nothing returned in this method
                }

                //get their info and cache it
                HttpGet httpGetUserInfo = new HttpGet("http://egiurleo.scripts.mit.edu/getUserInfo.php?userId=" + id);
                HttpResponse userInfoResponse = httpclient.execute(httpGetUserInfo);

                if(userInfoResponse != null){
                    inputStream = response.getEntity().getContent();

                    //return the string to be cached
                    return convertStreamToString(inputStream);
                }

            }else{
                Log.e("Response: ", "no response");
            }
        } catch (IOException e){
            Log.e("Exception: ", "IOExcetion");
        }

        return null;
    }

    protected void onPostExecute(String result){
        //cache results
        String fileName = "user_info";
        String string = result;

        try{
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            fos.write(string.getBytes());
            fos.close();
        }catch (IOException e){
            Log.e("Exception: ", "File not found.");
        }

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
