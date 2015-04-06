package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

/**
 * Created by egiurleo on 4/5/15.
 */
public class EditProfileActivity extends ConnectUSActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.editprofile);

        InputMethodManager im = (InputMethodManager) this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void doneEditing(View view){
        this.finish();
    }

}
