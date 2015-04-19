package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Jon M Beaulieu Jr on 4/12/15.
 */
public class AsyncProfile extends AsyncTask<Void, Void, String[]> {

    private Context context;
    private Activity activity;

    public AsyncProfile(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected String[] doInBackground(Void... args){

        String returnString = "";

        try {

            //get the stored file
            FileInputStream fileinput = context.openFileInput("connectus_user_info");
            Scanner scanner = new Scanner(fileinput);

            while(scanner.hasNext()){
                returnString = scanner.nextLine();
            }

            return returnString.split("\\|");


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
        String willingToHelp = result[6];
        String lookingForHelp = result[7];

        TextView txtView = (TextView) activity.findViewById(R.id.name);
        txtView.setText("Name: " + fullName);

        txtView = (TextView) activity.findViewById(R.id.country);
        txtView.setText("Country of Origin: " + country);

        txtView = (TextView) activity.findViewById(R.id.languages);
        txtView.setText("Languages: " + languages);

        txtView = (TextView) activity.findViewById(R.id.email);
        txtView.setText("Email: " + email);

        txtView = (TextView) activity.findViewById(R.id.phone);
        txtView.setText("Phone: " + phone);

        txtView = (TextView) activity.findViewById(R.id.willing_to_help);
        if(willingToHelp.equals("1")){
            txtView.setVisibility(View.VISIBLE);
        }else{
            txtView.setVisibility(View.INVISIBLE);
        }

        txtView = (TextView) activity.findViewById(R.id.looking_for_help);
        if(lookingForHelp.equals("1")){
            txtView.setVisibility(View.VISIBLE);
        }else{
            txtView.setVisibility(View.INVISIBLE);
        }

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            activity.findViewById(R.id.network_warning).setVisibility(View.VISIBLE);
        }

    }
}