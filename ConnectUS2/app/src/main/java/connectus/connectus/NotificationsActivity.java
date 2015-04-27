package connectus.connectus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;


public class NotificationsActivity extends ConnectUSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar
        setContentView(R.layout.activity_notifications);

        AsyncNotifications asyncNotifications = new AsyncNotifications(getApplicationContext(), NotificationsActivity.this);
        asyncNotifications.execute();

        //have the file cache
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        ConnectivityManager cm =
                                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        boolean isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();

                        if (isConnected) {

                            AsyncNotifications asyncNotifications = new AsyncNotifications(getApplicationContext(), NotificationsActivity.this);
                            asyncNotifications.execute();
                        }
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 0, 30000); //execute in every 30 seconds

    }


}
