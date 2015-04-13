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
        System.out.println("in asynclogin");
        HttpClient httpclient = new DefaultHttpClient();

        String id;
        String firstName = null;
        String lastName = null;

        if(args.length == 3) {
            id = args[0];
            firstName = args[1];
            lastName = args[2];

        }else{
            id = args[0];
        }

        //if this username exists...
        HttpGet httpUserExists = new HttpGet("http://egiurleo.scripts.mit.edu/checkUserExists.php?userId=" + id);

        try{
            HttpResponse response = httpclient.execute(httpUserExists);

            if(response != null){
                InputStream inputStream = response.getEntity().getContent();

                String q = convertStreamToString(inputStream);

                //if this user doesn't exist, put their information on the db
                if(q.equals("false")){
                    HttpGet httpRegister = new HttpGet("http://egiurleo.scripts.mit.edu/register.php?userId=" + id + "&firstName=" + firstName + "&lastName=" + lastName);
                    HttpResponse registerResponse = httpclient.execute(httpRegister);
                }

                //get their info and cache it
                System.out.println("getting response");
                HttpGet httpGetUserInfo = new HttpGet("http://egiurleo.scripts.mit.edu/getUserInfo.php?userId=" + id);
                HttpResponse userInfoResponse = httpclient.execute(httpGetUserInfo);

                if(userInfoResponse != null){
                    InputStream inputStream2 = userInfoResponse.getEntity().getContent();

                    //return the string to be cached
                    String x = convertStreamToString(inputStream2);
                    System.out.println("From async login: " + x);
                    return x;
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
        System.out.println("caching");
        String fileName = "connectus_user_info";
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
