package connectus.connectus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by egiurleo on 4/5/15.
 */
public class EditProfileActivity extends ConnectUSActivity {

    public String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.editprofile);

        InputMethodManager im = (InputMethodManager) this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        AsyncEditProfile asyncEditProfile = new AsyncEditProfile(EditProfileActivity.this, getApplicationContext(), this);
        asyncEditProfile.execute();
    }

    public void doneEditing(View view){

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            AsyncDoneEditing asyncDoneEditing = new AsyncDoneEditing(EditProfileActivity.this, userId, getApplicationContext());
            asyncDoneEditing.execute();

            AsyncLogin asyncLogin = new AsyncLogin(getApplicationContext());
            asyncLogin.execute(userId);

            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
        }

        this.finish();
    }

    @Override
    public void onBackPressed(){
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            System.out.println("connected in edit profile activity");
            AsyncDoneEditing asyncDoneEditing = new AsyncDoneEditing(EditProfileActivity.this, userId, getApplicationContext());
            asyncDoneEditing.execute();

            AsyncLogin asyncLogin = new AsyncLogin(getApplicationContext());
            asyncLogin.execute(userId);

            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);

        }

        super.onBackPressed();
    }

}
