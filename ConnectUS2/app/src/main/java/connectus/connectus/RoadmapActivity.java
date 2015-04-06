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
import android.view.MenuItem;

/**
 * Created by Jon M Beaulieu Jr on 3/27/2015.
 */
public class RoadmapActivity extends ConnectUSActivity {
    private TextView userDisplay;
    private String username;
    private String friendName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.roadmap);

        //get user object and display stuff based on that
        Intent intent = getIntent();
        username = intent.getStringExtra("user");

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

    public void mapStepClick(View view){
        Intent mapStepIntent = new Intent(RoadmapActivity.this, MapStepActivity.class);
        mapStepIntent.putExtra("user", username);

        RoadmapActivity.this.startActivity(mapStepIntent);
    }

}