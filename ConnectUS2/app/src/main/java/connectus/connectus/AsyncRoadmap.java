package connectus.connectus;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by egiurleo on 4/12/15.
 */
public class AsyncRoadmap extends AsyncTask<Void, Void, String[]> {

    private Context context;

    public AsyncRoadmap(Context context){
        this.context = context;
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

        //do stuff to map here
    }


}
