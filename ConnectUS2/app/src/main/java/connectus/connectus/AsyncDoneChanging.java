package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by egiurleo on 4/15/15.
 */
public class AsyncDoneChanging extends AsyncTask<Void, Void, Void> {

    private Context context;
    private Activity activity;
    private String id;

    public AsyncDoneChanging(Context context, Activity activity, String id) {
        this.context = context;
        this.activity = activity;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... args) {

        Spinner nameSpinner = (Spinner) activity.findViewById(R.id.name_spinner);
        Spinner emailSpinner = (Spinner) activity.findViewById(R.id.email_spinner);
        Spinner phoneSpinner = (Spinner) activity.findViewById(R.id.phone_spinner);
        Spinner countrySpinner = (Spinner) activity.findViewById(R.id.country_spinner);
        Spinner languageSpinner = (Spinner) activity.findViewById(R.id.langauge_spinner);

        Spinner[] spinners = {nameSpinner, emailSpinner, phoneSpinner, countrySpinner, languageSpinner};
        int[] visibility = new int[5];

        for (int i = 0; i < spinners.length; i++) {
            String visibilitySetting = spinners[i].getSelectedItem().toString();
            if (visibilitySetting.equals("Friends Only")) {
                visibility[i] = 0;
            } else {
                visibility[i] = 1;
            }
        }

        String urlString = "userId=" + id + "&name=" + visibility[0] + "&email=" + visibility[1] + "&phone=" + visibility[2] +
                "&country=" + visibility[3] + "&language=" + visibility[4];
        urlString = urlString.replace(" ", "+");

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://egiurleo.scripts.mit.edu/visibilitySettings.php?" + urlString);
            HttpResponse response = httpclient.execute(httpGet);
        } catch (IOException e) {
            Log.e("Exception", "IOException");
        }

        return null;
    }
}
