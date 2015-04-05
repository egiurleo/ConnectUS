package connectus.connectus;

import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by egiurleo on 4/5/15.
 */
public class EditProfileActivity extends ConnectUSActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.editprofile);
    }

}
