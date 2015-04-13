package connectus.connectus;

import android.app.Activity;
import android.content.Context;
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
    private RoadmapActivity rma;

    public AsyncRoadmap(Context context, Activity activity, RoadmapActivity rma){
        this.context = context;
        this.activity = activity;
        this.rma = rma;
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
            System.out.println(returnString);
            return returnString.split("\\|");


        } catch (IOException e){
            Log.e("Error: ", "file not found");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] result){
        int mapPos = Integer.parseInt(result[10]);

        System.out.println("mapPos: " + mapPos);

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

        rma.mapPos = mapPos;
    }


}
