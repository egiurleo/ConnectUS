package connectus.connectus;

import android.os.Bundle;
import android.view.WindowManager;

public class FindFriendsActivity extends ConnectUSActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.findfriends);

        AsyncFindFriends asyncFindFriends = new AsyncFindFriends(getApplicationContext(), FindFriendsActivity.this);
        asyncFindFriends.execute();
    }
}
