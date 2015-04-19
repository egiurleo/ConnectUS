package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LogTest extends AsyncTask <Void, Void, Void> {

    private StringBuilder log;
    private String id;

    private Context context;
    private Activity activity;
    private RoadmapActivity rma;

    public LogTest(Context context, Activity activity, RoadmapActivity rma, String id){
        this.context = context;
        this.activity = activity;
        this.rma = rma;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void...args) {
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            log=new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
            }

        } catch (IOException e) {
            Log.e("Ioexception", "bloop");
        }


        //convert log to string
        final String logString = new String(log.toString());

        //create text file in SDCard
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + "/myLogcat");
        dir.mkdirs();
        File file = new File(dir, "logcat.txt");

        try {
            //to write logcat in text file
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            osw.write(logString);
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }
}
