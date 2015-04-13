package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    private MyProfileActivity mpa;

    public AsyncProfile(Context context, Activity activity, MyProfileActivity mpa){
        this.context = context;
        this.activity = activity;
        this.mpa = mpa;
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
}