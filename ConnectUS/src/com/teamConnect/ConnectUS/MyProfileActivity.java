package com.teamConnect.ConnectUS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.Override;

public class MyProfgit ileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  //Hides app title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.myprofile);
    }

    public void changeVisibility(View view){
        Intent changeVisibilityIntent = new Intent(myProfileActivity.this, changeVisibilityActivity.class);
        changeVisbilityIntent.putExtra("user", username);     //Pass username to next activity
        MyProfileActivity.this.startActivity(changeVisibilityIntent);
    }



}