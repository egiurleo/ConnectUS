package connectus.connectus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
/**
 * Created by egiurleo on 3/31/15.
 */
public class OtherProfileActivity extends ConnectUSActivity {

    private String username;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.otherprofile);

        Intent intent = getIntent();
        String friendId = intent.getStringExtra("friend");
        //TextView text = (TextView)findViewById(R.id.friendId);
        //text.setText("Profile: " + friendId);
    }
}
