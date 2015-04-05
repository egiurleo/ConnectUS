package connectus.connectus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.lang.Override;

public class ChangeVisibilityActivity extends ConnectUSActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.changevisibility);
    }

}