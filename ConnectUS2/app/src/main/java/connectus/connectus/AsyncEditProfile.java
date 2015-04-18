package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class AsyncEditProfile extends AsyncTask <Void, Void, String[]> {

    private Activity activity;
    private Context context;
    private EditProfileActivity editProfileActivity;

    public AsyncEditProfile(Activity activity, Context context, EditProfileActivity editProfileActivity){
        this.activity = activity;
        this.context = context;
        this.editProfileActivity = editProfileActivity;
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

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            activity.findViewById(R.id.network_warning).setVisibility(View.VISIBLE);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] result){
        String id = result[0];
        String name = result[1];
        String email = result[2];
        String phone = result[3];
        String country = result[4];
        String languages = result[5];
        int willingToHelp = Integer.parseInt(result[6]);
        int lookingForHelp = Integer.parseInt(result[7]);

        editProfileActivity.userId = id;

        EditText nameFillin = (EditText) activity.findViewById(R.id.name_fillin);
        EditText languagesFillin = (EditText) activity.findViewById(R.id.languages_fillin);
        EditText emailFillin = (EditText) activity.findViewById(R.id.email_fillin);
        EditText phoneFillin = (EditText) activity.findViewById(R.id.phone_fillin);

        CheckBox willingToHelpCB = (CheckBox) activity.findViewById(R.id.help_check);
        CheckBox lookingForHelpCB = (CheckBox) activity.findViewById(R.id.needhelp_check);

        Spinner countries = (Spinner) activity.findViewById(R.id.country_fillin);

        nameFillin.setText(name);
        languagesFillin.setText(languages);
        emailFillin.setText(email);
        phoneFillin.setText(phone);

        if(willingToHelp == 1){
            willingToHelpCB.setChecked(true);
        }

        if(lookingForHelp == 1){
            lookingForHelpCB.setChecked(true);
        }

        if(!country.equals("")){
            int spinnerIndex = getIndex(countries, country);
            countries.setSelection(spinnerIndex);
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
