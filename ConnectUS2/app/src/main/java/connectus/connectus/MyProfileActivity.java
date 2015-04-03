package connectus.connectus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.*;

import java.lang.Override;

public class MyProfileActivity extends Activity {

    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  //Hides app title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.myprofile);

        //get user object and display stuff based on that
        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        this.updateView();
    }

    public void updateView(){
        //update info about the user
    }

    public void editProfile(View view){
        //edit profile
    }

    public void changeVisibility(View view){
        Intent changeVisibilityIntent = new Intent(MyProfileActivity.this, ChangeVisibilityActivity.class);
        changeVisibilityIntent.putExtra("user", username);     //Pass username to next activity
        MyProfileActivity.this.startActivity(changeVisibilityIntent);
    }



}