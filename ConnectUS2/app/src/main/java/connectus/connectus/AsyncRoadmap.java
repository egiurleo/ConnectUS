package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
 * Created by egiurleo on 4/12/15.
 */
public class AsyncRoadmap extends AsyncTask<Void, Void, String[]> {

    private Context context;
    private Activity activity;
    private RoadmapActivity rma;
    private String[] mapPosArray;
    private String[] nameArray;
    private String[] friendsList;
    private boolean visible;

    public AsyncRoadmap(Context context, Activity activity, RoadmapActivity rma){
        this.context = context;
        this.activity = activity;
        this.rma = rma;
        visible = true;
    }

    @Override
    protected String[] doInBackground(Void... args){

        String returnString = "";
        String[] userInfo = null;

        try {

            //get the stored file
            FileInputStream fileinput = context.openFileInput("connectus_user_info");
            Scanner scanner = new Scanner(fileinput);

            while(scanner.hasNext()){
                returnString = scanner.nextLine();
            }

            userInfo = returnString.split("\\|");
            friendsList = userInfo[8].split(" ");

        } catch (IOException e){
            Log.e("Error: ", "file not found");
        }

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            Log.e("should be", "connected");


            HttpClient httpclient = new DefaultHttpClient();

            mapPosArray = new String[friendsList.length];
            nameArray = new String[friendsList.length];
            for (int i = 0; i < friendsList.length; i++) {
                try {
                    HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/getMapPos.php?userId=" + friendsList[i]);
                    HttpResponse response = httpclient.execute(httprequest);

                    if (response != null) {
                        InputStream inputStream = response.getEntity().getContent();

                        //return the string to be cached
                        String pos = convertStreamToString(inputStream);
                        mapPosArray[i] = pos;
                    }
                } catch (IOException e) {
                    Log.e("Exception", "IOException");
                }

                try {
                    HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/getName.php?userId=" + friendsList[i]);
                    HttpResponse response = httpclient.execute(httprequest);

                    if (response != null) {
                        InputStream inputStream = response.getEntity().getContent();

                        //return the string to be cached
                        String name = convertStreamToString(inputStream);
                        nameArray[i] = name;
                    }
                } catch (IOException e) {
                    Log.e("Exception", "IOException");
                }

            }
        }else{
            activity.findViewById(R.id.network_warning).setVisibility(View.VISIBLE);
            visible = false;
        }

         return userInfo;
    }

    @Override
    protected void onPostExecute(String[] result){
        int mapPos = Integer.parseInt(result[10]);

        for(int i = 1; i <= mapPos; i++){

            if(i==6){
                String badID = "step5";
                int resourceId = context.getResources().getIdentifier(badID, "id", context.getPackageName());
                ImageView imgView = (ImageView) activity.findViewById(resourceId);
                imgView.setVisibility(View.INVISIBLE);
            }

            String id = "step" + i;
            int resourceId = context.getResources().getIdentifier(id, "id", context.getPackageName());
            ImageView imgView = (ImageView) activity.findViewById(resourceId);
            imgView.setVisibility(View.VISIBLE);

        }

        for(int i = mapPos + 1; i <= 6; i++){
            String id = "step" + i;
            int resourceId = context.getResources().getIdentifier(id, "id", context.getPackageName());
            ImageView imgView = (ImageView) activity.findViewById(resourceId);
            imgView.setVisibility(View.INVISIBLE);
        }

        if(visible) {

            for(int i=1; i<=6; i++){
                String id = "dot" + i;
                int resourceId = context.getResources().getIdentifier(id, "id", context.getPackageName());
                ImageView imgView = (ImageView) activity.findViewById(resourceId);
                imgView.setTag("");
                imgView.setVisibility(View.GONE);
            }

            rma.mapPos = mapPos;
            if (!friendsList[0].equals("")) {
                for (int i = 0; i < friendsList.length; i++) {
                    String pos = mapPosArray[i];
                    String id = "dot" + pos;
                    int resourceId = context.getResources().getIdentifier(id, "id", context.getPackageName());
                    ImageView imgView = (ImageView) activity.findViewById(resourceId);

                    if (imgView.getTag() == null) {
                        imgView.setTag("");
                    }

                    imgView.setTag(imgView.getTag() + "|" + friendsList[i] + "," + nameArray[i]);
                    imgView.setVisibility(View.VISIBLE);
                }
            }
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
