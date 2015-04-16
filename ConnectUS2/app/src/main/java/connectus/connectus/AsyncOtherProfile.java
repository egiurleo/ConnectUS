package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Jon M Beaulieu Jr on 4/12/15.
 */
public class AsyncOtherProfile extends AsyncTask<Void, Void, String[]> {

    private Context context;
    private Activity activity;
    private OtherProfileActivity opa;

    public AsyncOtherProfile(Context context, Activity activity, OtherProfileActivity opa){
        this.context = context;
        this.activity = activity;
        this.opa = opa;
    }

    boolean areFriends = false;

    @Override
    protected String[] doInBackground(Void... args){
        HttpClient httpclient = new DefaultHttpClient();

        String returnString = "";

        try {

            //get the stored file
            FileInputStream fileinput = context.openFileInput("connectus_user_info");
            Scanner scanner = new Scanner(fileinput);

            while(scanner.hasNext()){
                returnString = scanner.nextLine();
            }

            String[] splitList = returnString.split("\\|");
            String[] friendsList = splitList[7].split(" ");

            HttpGet httpGetUserInfo = new HttpGet("http://egiurleo.scripts.mit.edu/getUserInfo.php?userId=" + id);
            HttpResponse userInfoResponse = httpclient.execute(httpGetUserInfo);

            if(userInfoResponse != null){
                InputStream inputStream2 = userInfoResponse.getEntity().getContent();

                //return the string to be cached
                String x = convertStreamToString(inputStream2);
                String[] splitProperties = x.split("\\|");

                return splitProperties;
            }


        } catch (IOException e){
            Log.e("Error: ", "file not found");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] result){
        String userId = result[0];
        String fullName = result[1];
        String email = result[2];
        String phone = result[3];
        String country = result[4];
        String languages = result[5];


            int resourceId = Resources.getSystem().getIdentifier("name", "id", context.getPackageName());
            TextView txtView = (TextView) activity.findViewById(resourceId);
            txtView.setText("Name: " + fullName);

            resourceId = Resources.getSystem().getIdentifier("country", "id", context.getPackageName());
            txtView = (TextView) activity.findViewById(resourceId);
            txtView.setText("Country of Origin: " + country);

            resourceId = Resources.getSystem().getIdentifier("languages", "id", context.getPackageName());
            txtView = (TextView) activity.findViewById(resourceId);
            txtView.setText("Languages: " + languages);

            resourceId = Resources.getSystem().getIdentifier("email", "id", context.getPackageName());
            txtView = (TextView) activity.findViewById(resourceId);
            txtView.setText("Email: " + email);

            resourceId = Resources.getSystem().getIdentifier("phone", "id", context.getPackageName());
            txtView = (TextView) activity.findViewById(resourceId);
            txtView.setText("Phone: " + phone);
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