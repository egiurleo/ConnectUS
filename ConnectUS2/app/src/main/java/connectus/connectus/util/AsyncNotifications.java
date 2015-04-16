package connectus.connectus.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

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

import connectus.connectus.OtherProfileActivity;
import connectus.connectus.R;

/**
 * Created by egiurleo on 4/16/15.
 */
public class AsyncNotifications extends AsyncTask<Void, Void, Void> {

    private Context context;
    private final Activity activity;
    private String[] notifications;
    private String userId;
    private View.OnClickListener onClickFriend;
    private View.OnClickListener acceptFriend;
    private View.OnClickListener rejectFriend;
    private String[] names;

    public AsyncNotifications(Context context, Activity activity){
        this.context = context;
        this.activity = activity;

        onClickFriend = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) v.getTag();
                Intent otherProfileIntent = new Intent(AsyncNotifications.this.activity, OtherProfileActivity.class);
                otherProfileIntent.putExtra("id", tag);

                AsyncNotifications.this.activity.startActivity(otherProfileIntent);

            }
        };

        acceptFriend = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //blah blah
            }
        };

        rejectFriend = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //blah blah
            }
        };



    }

    @Override
    protected Void doInBackground(Void... args){

        String returnString = "";

        try {

            //get the stored file
            FileInputStream fileinput = context.openFileInput("connectus_user_info");
            Scanner scanner = new Scanner(fileinput);

            while(scanner.hasNext()){
                returnString = scanner.nextLine();
            }

            String[] userInfo = returnString.split("\\|");
            notifications = userInfo[11].split(" ");
            userId = userInfo[0];


        } catch (IOException e){
            Log.e("Error: ", "file not found");
        }

        HttpClient httpclient = new DefaultHttpClient();

        names = new String[notifications.length];
        for(int i=0; i<notifications.length; i++){
            try {
                HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/getName.php?userId=" + notifications[i]);
                HttpResponse response = httpclient.execute(httprequest);

                if(response != null){
                    InputStream inputStream2 = response.getEntity().getContent();

                    //return the string to be cached
                    String name = convertStreamToString(inputStream2);
                    names[i] = name;
                }
            }catch (IOException e){
                Log.e("Exception", "IOException");
            }


        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        LinearLayout notificationsContainer = (LinearLayout) activity.findViewById(R.id.notifications);

        for(int i=0; i<notifications.length; i++){
            //create linear layout
            LinearLayout layout = new LinearLayout(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.setMargins(10, 10, 10, 0);
            layout.setPadding(10, 10, 10, 10);
            layout.setBackgroundColor(Color.parseColor("#DDDDDD"));
            layout.setOnClickListener(onClickFriend);
            layout.setTag(notifications[i]);

            //create textview
            TextView textView = new TextView(activity);
            textView.setText(names[i]);
            ViewGroup.LayoutParams textViewLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(textViewLayoutParams);
            textView.setTextSize(20);

            //create check
            ImageView imageView = new ImageView(activity);
            LayoutParams layoutParams = new LayoutParams(30, 30);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.check2);
            imageView.setOnClickListener(acceptFriend);

            //create x
            ImageView imageView2 = new ImageView(activity);
            LayoutParams layoutParams2 = new LayoutParams(30, 30);
            imageView2.setLayoutParams(layoutParams2);
            imageView2.setImageResource(R.drawable.x);
            imageView2.setOnClickListener(rejectFriend);

            layout.addView(textView);
            layout.addView(imageView);
            layout.addView(imageView2);
            notificationsContainer.addView(layout, params);
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
