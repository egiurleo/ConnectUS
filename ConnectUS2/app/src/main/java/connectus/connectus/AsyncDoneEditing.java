package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public class AsyncDoneEditing extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private String id;
    private Context context;

    public AsyncDoneEditing(Activity activity, String id, Context context){
        this.activity = activity;
        this.id = id;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... args){
        String name = ((EditText) activity.findViewById(R.id.name_fillin)).getText().toString();
        String languages = ((EditText) activity.findViewById(R.id.languages_fillin)).getText().toString();
        String email = ((EditText) activity.findViewById(R.id.email_fillin)).getText().toString();
        String phone = ((EditText) activity.findViewById(R.id.phone_fillin)).getText().toString();

        boolean willingToHelpB = ((CheckBox) activity.findViewById(R.id.help_check)).isChecked();
        boolean lookingForHelpB = ((CheckBox) activity.findViewById(R.id.needhelp_check)).isChecked();

        String willingToHelp;
        String lookingForHelp;

        if(willingToHelpB){
            willingToHelp = "1";
        }else{
            willingToHelp = "0";
        }

        if(lookingForHelpB){
            lookingForHelp = "1";
        }else{
            lookingForHelp = "0";
        }

        String country = ((Spinner) activity.findViewById(R.id.country_fillin)).getSelectedItem().toString();

        String urlString = "userId=" + id + "&name=" + name + "&languages=" + languages + "&email=" + email +
                "&phone=" + phone + "&willingToHelp=" + willingToHelp + "&lookingForHelp=" + lookingForHelp +
                "&country=" + country;
        urlString = urlString.replace(" ", "+");


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://egiurleo.scripts.mit.edu/setUserInfo.php?" + urlString);
            HttpResponse response = httpclient.execute(httpGet);
        }catch(IOException e){
            Log.e("Exception", "IOException");
        }

        return null;
    }

}
