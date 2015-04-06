package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class GoogleLoginActivity extends FragmentActivity {
    private LoginButton loginButton;
    private TextView message;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  //Hides app title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e("Login", "SUCCESS!");
                        startNextActivity();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e("Login", "CANCEL");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("Login", "Fail :(");
                    }
                });

    }
    public void startNextActivity() {
        Intent intent = new Intent(this, RoadmapActivity.class);
        startActivity(intent);

    }


}