package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by egiurleo on 4/15/15.
 */
public class AsyncChangeVisibility extends AsyncTask<Void, Void, String[]> {

    private Context context;
    private Activity activity;
    private ChangeVisibilityActivity changeVisibilityActivity;

    public AsyncChangeVisibility(Context context, Activity activity, ChangeVisibilityActivity changeVisibilityActivity){
        this.context = context;
        this.activity = activity;
        this.changeVisibilityActivity = changeVisibilityActivity;
    }

    @Override
    protected String[] doInBackground(Void... args){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            activity.findViewById(R.id.network_warning).setVisibility(View.VISIBLE);
        }

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

        if(result != null) {
            changeVisibilityActivity.id = result[0];

            String[] visibilitySettings = result[12].split(" ");
            //name, email, phone, country, language

            Spinner nameSpinner = (Spinner) activity.findViewById(R.id.name_spinner);
            Spinner emailSpinner = (Spinner) activity.findViewById(R.id.email_spinner);
            Spinner phoneSpinner = (Spinner) activity.findViewById(R.id.phone_spinner);
            Spinner countrySpinner = (Spinner) activity.findViewById(R.id.country_spinner);
            Spinner languageSpinner = (Spinner) activity.findViewById(R.id.langauge_spinner);

            Spinner[] spinners = {nameSpinner, emailSpinner, phoneSpinner, countrySpinner, languageSpinner};

            for (int i = 0; i < spinners.length; i++) {
                if (visibilitySettings[i].equals("1")) {
                    int spinnerIndex = getIndex(spinners[i], "Everyone");
                    spinners[i].setSelection(spinnerIndex);
                } else {
                    int spinnerIndex = getIndex(spinners[i], "Friends Only");
                    spinners[i].setSelection(spinnerIndex);
                }
            }
        }
    }

    private int getIndex(Spinner spinner, String value){
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(value)){
                index = i;
            }
        }
        return index;
    }
}
