package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
/**
 * Created by egiurleo on 3/31/15.
 */
public class OtherProfileActivity extends ConnectUSActivity {

    private String id;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.otherprofile);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        AsyncOtherProfile asyncOtherProfile = new AsyncOtherProfile(getApplicationContext(), OtherProfileActivity.this, this, id);
        asyncOtherProfile.execute();
        //TextView text = (TextView)findViewById(R.id.friendId);
        //text.setText("Profile: " + friendId);
    }
}
