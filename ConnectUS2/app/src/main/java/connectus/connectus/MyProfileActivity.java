package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MyProfileActivity extends ConnectUSActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.myprofile);

        AsyncProfile asyncProfile = new AsyncProfile(getApplicationContext(), MyProfileActivity.this);
        asyncProfile.execute();
    }


    public void changeVisibility(View view){
        Intent changeVisibilityIntent = new Intent(MyProfileActivity.this, ChangeVisibilityActivity.class);
        MyProfileActivity.this.startActivity(changeVisibilityIntent);
    }

    public void editProfile(View view){
        Intent editProfileIntent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
        MyProfileActivity.this.startActivityForResult(editProfileIntent, 2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("getting result");
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                AsyncProfile asyncProfile = new AsyncProfile(getApplicationContext(), MyProfileActivity.this);
                asyncProfile.execute();
            }
        }
    }


}