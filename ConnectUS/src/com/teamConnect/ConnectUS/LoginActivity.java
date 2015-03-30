package com.teamConnect.ConnectUS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    private Button loginButton;
    private EditText usernameField;
    private EditText passwordField;
    private String username;
    private String password;
    private TextView message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  //Hides app title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.main);

        InitializeApp();
    }

    private void InitializeApp() {
        loginButton = (Button)findViewById(R.id.loginButton);
        usernameField = (EditText)findViewById(R.id.userText);
        passwordField = (EditText)findViewById(R.id.passText);
        message = (TextView)findViewById(R.id.signupText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin()
    {
        username = usernameField.getText().toString();
        password = passwordField.getText().toString();
        //message.setText(username + " " + password);

        // TEST USER/PASS COMBO HERE
        Intent loginIntent = new Intent(LoginActivity.this, RoadmapActivity.class);
        loginIntent.putExtra("user", username);     //Pass username to next activity
        LoginActivity.this.startActivity(loginIntent);
    }

    public void sendMessage(View view) {
        // Do something in response to button
        message.setText(username + " " + password);
    }
}