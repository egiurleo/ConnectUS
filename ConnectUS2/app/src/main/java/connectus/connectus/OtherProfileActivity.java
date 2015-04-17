package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    }

    public void sendFriendRequest(View view){
        Log.e("in method", "send friend request");
        AsyncSendFriendRequest asyncSendFriendRequest = new AsyncSendFriendRequest(id, getApplicationContext(), OtherProfileActivity.this);
        asyncSendFriendRequest.execute();
    }
}
