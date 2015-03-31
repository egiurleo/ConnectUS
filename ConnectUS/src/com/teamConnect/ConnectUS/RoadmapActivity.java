package com.teamConnect.ConnectUS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Jon M Beaulieu Jr on 3/27/2015.
 */
public class RoadmapActivity extends Activity {
    private TextView userDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  //Hides app title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.roadmap);

        //get user object and display stuff based on that
        Intent intent = getIntent();
        String username = intent.getStringExtra("user");
        userDisplay = (TextView)findViewById(R.id.textView);
        userDisplay.setText("username: " + username);
    }

    public void myProfileClick(){
        Intent myProfileIntent = new Intent(RoadmapActivity.this, MyProfileActivity.class);
        myProfileIntent.putExtra("user", username);     //Pass username to next activity
        RoadmapActivity.this.startActivity(myProfileIntent);
    }

}