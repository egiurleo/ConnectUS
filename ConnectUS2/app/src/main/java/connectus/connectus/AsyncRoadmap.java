package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by egiurleo on 4/12/15.
 */
public class AsyncRoadmap extends AsyncTask<Void, Void, String[]> {

    private Context context;
    private Activity activity;

    public AsyncRoadmap(Context context, Activity activity){
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
        int mapPos = Integer.parseInt(result[10]);

        for(int i = 1; i <= mapPos; i++){
            String id = "step" + i;
            int resourceId = Resources.getSystem().getIdentifier(id, "id", "android");
            ImageView imgView = (ImageView) activity.findViewById(resourceId);
            imgView.setVisibility(View.VISIBLE);
        }
    }


}
