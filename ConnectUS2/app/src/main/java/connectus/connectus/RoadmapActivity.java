package connectus.connectus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by Jon M Beaulieu Jr on 3/27/2015.
 */
public class RoadmapActivity extends Activity {
    private TextView userDisplay;
    private String username;
    private String friendName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);  //Hides app title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.roadmap);

        //get user object and display stuff based on that
        Intent intent = getIntent();
        username = intent.getStringExtra("user");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    public void myProfileClick(View view){
        Intent myProfileIntent = new Intent(RoadmapActivity.this, MyProfileActivity.class);
        myProfileIntent.putExtra("user", username);     //Pass username to next activity
        RoadmapActivity.this.startActivity(myProfileIntent);
    }

    public void friendProfileClick(View view){
        Intent otherProfileIntent = new Intent(RoadmapActivity.this, OtherProfileActivity.class);
        otherProfileIntent.putExtra("user", username);

        RoadmapActivity.this.startActivity(otherProfileIntent);
    }
    
    public void friendsListClick(View view){
        Intent friendsListIntent = new Intent(RoadmapActivity.this, FriendsListActivity.class);
        friendsListIntent.putExtra("user", username);

        RoadmapActivity.this.startActivity(friendsListIntent);
    }

}