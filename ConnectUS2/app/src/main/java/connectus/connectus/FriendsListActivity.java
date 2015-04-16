package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class FriendsListActivity extends ConnectUSActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar
        setContentView(R.layout.friendslist);

        //load friends onto screen
        AsyncFriendsList asyncFriendsList = new AsyncFriendsList(getApplicationContext(), FriendsListActivity.this);
        asyncFriendsList.execute();
    }

    public void findFriendsClick(View view){
        Intent findFriendsIntent = new Intent(FriendsListActivity.this, FindFriendsActivity.class);
        FriendsListActivity.this.startActivity(findFriendsIntent);
    }

}
