package connectus.connectus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.WindowManager;


public class NotificationsActivity extends ConnectUSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar
        setContentView(R.layout.activity_notifications);
    }

}
