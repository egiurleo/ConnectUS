package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Jon M Beaulieu Jr on 4/12/15.
 */
public class AsyncOtherProfile extends AsyncTask<Void, Void, String[]> {

    private Context context;
    private Activity activity;
    private OtherProfileActivity opa;
    private String id;

    public AsyncOtherProfile(Context context, Activity activity, OtherProfileActivity opa, String id){
        this.context = context;
        this.activity = activity;
        this.opa = opa;
        this.id = id;
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
            String[] friendsList = splitList[8].split(" ");

            if(Arrays.asList(friendsList).contains(id))
            {
                areFriends = true;
            }

            HttpGet httpGetUserInfo = new HttpGet("http://egiurleo.scripts.mit.edu/getUserInfo.php?userId=" + id);
            HttpResponse userInfoResponse = httpclient.execute(httpGetUserInfo);


            if(userInfoResponse != null){
                InputStream inputStream2 = userInfoResponse.getEntity().getContent();

                //return the string to be cached
                String x = convertStreamToString(inputStream2);
                Log.e("response", x);
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
        int willingToHelp = Integer.parseInt(result[6]);
        int lookingForHelp = Integer.parseInt(result[7]);
        String[] visibility = result[12].split(" ");

        TextView txtView;

        if(areFriends) {
            txtView = (TextView) activity.findViewById(R.id.name);
            txtView.setText("Name: " + fullName);

            txtView = (TextView) activity.findViewById(R.id.email);
            txtView.setText("Email: " + email);

            txtView = (TextView) activity.findViewById(R.id.phone);
            txtView.setText("Phone: " + phone);

            txtView = (TextView) activity.findViewById(R.id.country);
            txtView.setText("Country of Origin: " + country);

            txtView = (TextView) activity.findViewById(R.id.languages);
            txtView.setText("Languages: " + languages);

            activity.findViewById(R.id.send_friend_request).setVisibility(View.INVISIBLE);
        }
        else {
            if(visibility[0].equals("1")) {
                txtView = (TextView) activity.findViewById(R.id.name);
                txtView.setText("Name: " + fullName);
            }
            if(visibility[1].equals("1")) {
                txtView = (TextView) activity.findViewById(R.id.email);
                txtView.setText("Email: " + email);
            }
            if(visibility[2].equals("1")) {
                txtView = (TextView) activity.findViewById(R.id.phone);
                txtView.setText("Phone: " + phone);
            }
            if(visibility[3].equals("1")) {
                txtView = (TextView) activity.findViewById(R.id.country);
                txtView.setText("Country of Origin: " + country);
            }

            Log.e("visibility", visibility[4]);
            if(visibility[4].equals("1")) {

                txtView = (TextView) activity.findViewById(R.id.languages);
                txtView.setText("Languages: " + languages);
            }
        }

        if(willingToHelp == 1){
            TextView textView = (TextView) activity.findViewById(R.id.willing_to_help);
            textView.setVisibility(View.VISIBLE);
        }

        if(lookingForHelp == 1){
            TextView textView = (TextView) activity.findViewById(R.id.looking_for_help);
            textView.setVisibility(View.VISIBLE);
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