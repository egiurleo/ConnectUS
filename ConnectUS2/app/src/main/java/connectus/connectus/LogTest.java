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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.os.Environment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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

            final Handler handler = new Handler();
            Timer timer = new Timer();
            TimerTask doAsynchronousTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            String ret = "";

                            try {
                                File sdCard = Environment.getExternalStorageDirectory();
                                InputStream inputStream = context.openFileInput(sdCard.getAbsolutePath() + "/myLogcat/logcat.txt");

                                if ( inputStream != null ) {
                                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                    String receiveString = "";
                                    StringBuilder stringBuilder = new StringBuilder();

                                    while ( (receiveString = bufferedReader.readLine()) != null ) {
                                        stringBuilder.append(receiveString);
                                    }

                                    inputStream.close();
                                    ret = stringBuilder.toString();

                                    HttpClient httpclient = new DefaultHttpClient();
                                    HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/writeLogFiles.php?userId=" + id + "&logs=" + ret);
                                    HttpResponse response = httpclient.execute(httprequest);
                                }
                            }
                            catch (FileNotFoundException e) {
                                Log.e("login activity", "File not found: " + e.toString());
                            } catch (IOException e) {
                                Log.e("login activity", "Can not read file: " + e.toString());
                            }
                        }
                    });
                }
            };

            timer.schedule(doAsynchronousTask, 0, 120000); //execute in every 2 min

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
