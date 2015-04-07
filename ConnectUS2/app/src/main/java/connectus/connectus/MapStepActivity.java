package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by egiurleo on 4/4/15.
 */
public class MapStepActivity extends ConnectUSActivity {

    private String buttonId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar
        setContentView(R.layout.mapstep);

        Intent intent = getIntent();
        buttonId = intent.getStringExtra("buttonID");

        createView();
    }

    public void createView(){

        TextView detailsTitle = (TextView) findViewById(R.id.details_title);
        TextView detailsText = (TextView) findViewById(R.id.details_text);

        System.out.println(buttonId + "_title");

        int titleId = getResources().getIdentifier(buttonId + "_title", "string", getApplicationContext().getPackageName());
        int textId = getResources().getIdentifier(buttonId + "_text", "string", getApplicationContext().getPackageName());

        detailsTitle.setText(titleId);
        detailsText.setText(textId);

    }

}
