package connectus.connectus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
public class AsyncNotifications extends AsyncTask<Void, Void, Void> {

    private Context context;
    private final Activity activity;
    private String[] notifications;
    private String[] friends;
    private String userId;
    private View.OnClickListener onClickFriend;
    private View.OnClickListener acceptFriend;
    private View.OnClickListener rejectFriend;
    private String[] names;
    private boolean connected;

    public AsyncNotifications(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        connected = true;

        onClickFriend = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) v.getTag();
                Intent otherProfileIntent = new Intent(AsyncNotifications.this.activity, OtherProfileActivity.class);
                otherProfileIntent.putExtra("id", tag);

                AsyncNotifications.this.activity.startActivity(otherProfileIntent);

                RelativeLayout parent = (RelativeLayout) v.getParent().getParent();
                parent.setVisibility(View.GONE);

            }
        };

        acceptFriend = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String notificationId = (String) v.getTag();
                AsyncNotifications.this.notifications = remove(AsyncNotifications.this.notifications, notificationId);
                AsyncNotifications.this.friends = add(AsyncNotifications.this.friends, notificationId);

                String notifString = asString(AsyncNotifications.this.notifications);
                String friendsString = asString(AsyncNotifications.this.friends);

                AsyncSetNotification asyncSetNotification = new AsyncSetNotification(
                        AsyncNotifications.this.userId, notifString, true, friendsString);
                asyncSetNotification.execute();

                AsyncLogin asyncLogin = new AsyncLogin(AsyncNotifications.this.context);
                asyncLogin.execute(AsyncNotifications.this.userId);

                RelativeLayout parent = (RelativeLayout) v.getParent().getParent();
                parent.setVisibility(View.GONE);

            }
        };

        rejectFriend = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String notificationId = (String) v.getTag();
                AsyncNotifications.this.notifications = remove(AsyncNotifications.this.notifications, notificationId);
                String notifString = asString(AsyncNotifications.this.notifications);
                AsyncSetNotification asyncSetNotification = new AsyncSetNotification(
                        AsyncNotifications.this.userId, notifString, false, "");
                asyncSetNotification.execute();

                AsyncLogin asyncLogin = new AsyncLogin(AsyncNotifications.this.context);
                asyncLogin.execute(AsyncNotifications.this.userId);

                RelativeLayout parent = (RelativeLayout) v.getParent().getParent();
                parent.setVisibility(View.GONE);
            }
        };



    }

    @Override
    protected Void doInBackground(Void... args){

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {

            String returnString = "";

            try {

                //get the stored file
                FileInputStream fileinput = context.openFileInput("connectus_user_info");
                Scanner scanner = new Scanner(fileinput);

                while (scanner.hasNext()) {
                    returnString = scanner.nextLine();
                }

                String[] userInfo = returnString.split("\\|");
                notifications = userInfo[11].split(" ");
                Log.e("notifications", notifications[0]);
                friends = userInfo[8].split(" ");
                userId = userInfo[0];


            } catch (IOException e) {
                Log.e("Error: ", "file not found");
            }

            if (!notifications[0].equals("")) {

                HttpClient httpclient = new DefaultHttpClient();

                names = new String[notifications.length];
                for (int i = 0; i < notifications.length; i++) {
                    try {
                        HttpGet httprequest = new HttpGet("http://egiurleo.scripts.mit.edu/getName.php?userId=" + notifications[i]);
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
        }else{
            activity.findViewById(R.id.network_warning).setVisibility(View.VISIBLE);
            connected = false;
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result){

        if(connected) {


            LinearLayout notificationsContainer = (LinearLayout) activity.findViewById(R.id.notifications);

            if (!notifications[0].equals("")) {

                for (int i = 0; i < notifications.length; i++) {
                    //create linear layout
                    RelativeLayout layout = new RelativeLayout(activity);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    params.setMargins(10, 10, 10, 0);
                    layout.setPadding(10, 10, 10, 10);
                    layout.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    layout.setOnClickListener(onClickFriend);
                    layout.setTag(notifications[i]);

                    LinearLayout buttonHolder = new LinearLayout(activity);
                    RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams
                            (new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                    //create textview
                    TextView textView = new TextView(activity);
                    textView.setText(names[i]);
                    ViewGroup.LayoutParams textViewLayoutParams = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(textViewLayoutParams);
                    textView.setTextSize(20);

                    Button accept = new Button(activity);
                    LayoutParams acceptParams = new LayoutParams(30, 30);
                    accept.setLayoutParams(acceptParams);
                    accept.setBackgroundResource(R.drawable.check2);
                    accept.setTag(notifications[i]);
                    accept.setOnClickListener(acceptFriend);

                    Button reject = new Button(activity);
                    LinearLayout.LayoutParams rejectParams = new LinearLayout.LayoutParams(30, 30);
                    rejectParams.setMargins(20, 0, 0, 0);
                    reject.setLayoutParams(rejectParams);
                    reject.setBackgroundResource(R.drawable.x);
                    reject.setTag(notifications[i]);
                    reject.setOnClickListener(rejectFriend);

                    buttonHolder.addView(accept);
                    buttonHolder.addView(reject);

                    layout.addView(textView);
                    layout.addView(buttonHolder, llParams);
                    notificationsContainer.addView(layout, params);
                }
            } else {
                TextView noNotifications = (TextView) activity.findViewById(R.id.have_no_notifications);
                noNotifications.setVisibility(View.VISIBLE);

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

    private String[] remove(String[] array, String string){
        //assuming string is in array...
        String[] newArray = new String[array.length-1];

        int j = 0;
        for(int i=0; i<array.length; i++){
            if(!array[i].equals(string)){
                newArray[j] = array[i];
                j++;
            }
        }

        return newArray;
    }

    private String[] add(String[] array, String string){
        String[] newArray = new String[array.length + 1];

        for(int i=0; i<array.length; i++){
            newArray[i] = array[i];
        }

        newArray[newArray.length-1] = string;
        return newArray;
    }

    private String asString(String[] array){
        String returnString = "";

        for(String string : array){
            returnString = returnString + string + " ";
        }

        //get rid of the trailing space
        returnString = returnString.trim();
        return returnString;
    }



}
