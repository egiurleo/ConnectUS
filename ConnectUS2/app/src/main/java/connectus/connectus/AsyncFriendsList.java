package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by egiurleo on 4/16/15.
 */
public class AsyncFriendsList extends AsyncTask<Void, Void, Void> {

    private Context context;
    private final Activity activity;
    private String[] friendsList;
    private String userId;
    private OnClickListener onClickFriend;
    private String[] names;

    public AsyncFriendsList(Context context, Activity activity){
        this.context = context;
        this.activity = activity;

        onClickFriend = new OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) v.getTag();
                Intent otherProfileIntent = new Intent(AsyncFriendsList.this.activity, OtherProfileActivity.class);
                otherProfileIntent.putExtra("id", tag);

                AsyncFriendsList.this.activity.startActivity(otherProfileIntent);

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
            friendsList = userInfo[8].split(" ");
            userId = userInfo[0];


        } catch (IOException e){
            Log.e("Error: ", "file not found");
        }

        if(!friendsList[0].equals("")) {

            HttpClient httpclient = new DefaultHttpClient();

            names = new String[friendsList.length];
            for (int i = 0; i < friendsList.length; i++) {
                try {
                    HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/getName.php?userId=" + friendsList[i]);
                    HttpResponse response = httpclient.execute(httprequest);

                    if (response != null) {
                        InputStream inputStream2 = response.getEntity().getContent();

                        //return the string to be cached
                        String name = convertStreamToString(inputStream2);
                        names[i] = name;
                    }
                } catch (IOException e) {
                    Log.e("Exception", "IOException");
                }


            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        LinearLayout friendsContainer = (LinearLayout) activity.findViewById(R.id.friends);

        if(!friendsList[0].equals("")) {
            for (int i = 0; i < friendsList.length; i++) {
                //create linear layout
                LinearLayout layout = new LinearLayout(activity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                params.setMargins(10, 10, 10, 0);
                layout.setPadding(10, 10, 10, 10);
                layout.setBackgroundColor(Color.parseColor("#DDDDDD"));
                layout.setOnClickListener(onClickFriend);
                layout.setTag(friendsList[i]);

                //create textview
                TextView textView = new TextView(activity);
                textView.setText(names[i]);
                LayoutParams textViewLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(textViewLayoutParams);
                textView.setTextSize(20);

                layout.addView(textView);
                friendsContainer.addView(layout, params);

            }
        }else{
            TextView haveNoFriends = (TextView) activity.findViewById(R.id.have_no_friends);
            haveNoFriends.setVisibility(View.VISIBLE);
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
