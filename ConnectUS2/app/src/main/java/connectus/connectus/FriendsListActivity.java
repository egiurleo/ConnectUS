package connectus.connectus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FriendsListActivity extends ConnectUSActivity {

    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.friendslist);

        //get user object and display stuff based on that
        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        //load friends onto screen
        showFriends();
    }

    public void findFriendsClick(View view){
        Intent findFriendsIntent = new Intent(FriendsListActivity.this, FindFriendsActivity.class);
        findFriendsIntent.putExtra("user", username);     //Pass username to next activity
        FriendsListActivity.this.startActivity(findFriendsIntent);
    }

    public void showFriends(){
        String[] placeholderNames = {"Name 1", "Name 2", "Name 3"};

        ScrollView sv = (ScrollView) this.findViewById(R.id.scrollView);

        // Create a LinearLayout element
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        for (String name : placeholderNames){
            // Add text
            TextView tv = new TextView(this);
            tv.setText(name);
            ll.addView(tv);
        }

        // Add the LinearLayout element to the ScrollView
        sv.addView(ll);
        // Display the view
        setContentView(R.layout.friendslist);
    }

}
