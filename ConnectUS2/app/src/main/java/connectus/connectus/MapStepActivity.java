package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by egiurleo on 4/4/15.
 */
public class MapStepActivity extends ConnectUSActivity {

    private String buttonId;
    private int mapPos;
    private int myPos;
    private int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar
        setContentView(R.layout.mapstep);

        Intent intent = getIntent();
        buttonId = intent.getStringExtra("buttonID");

        mapPos = intent.getIntExtra("mapPos", 0);
        int myPosId = getResources().getIdentifier(buttonId + "_number", "string", getApplicationContext().getPackageName());
        myPos = Integer.parseInt(getString(myPosId));
        id = intent.getIntExtra("userId", 0);

        createView();
    }

    public void createView(){

        TextView detailsTitle = (TextView) findViewById(R.id.details_title);
        TextView detailsText = (TextView) findViewById(R.id.details_text);

        int titleId = getResources().getIdentifier(buttonId + "_title", "string", getApplicationContext().getPackageName());
        int textId = getResources().getIdentifier(buttonId + "_text", "string", getApplicationContext().getPackageName());

        detailsTitle.setText(titleId);
        detailsText.setText(textId);

        CheckBox checkbox = (CheckBox) findViewById(R.id.map_step_checkbox);
        //fix up checkbox
        if(mapPos >= myPos){
            checkbox.setChecked(true);
        }else if(mapPos < myPos - 1){
            checkbox.setEnabled(false);
        }

    }

    public void onCheckboxClick(View view){
        CheckBox checkbox = (CheckBox) findViewById(R.id.map_step_checkbox);

        if(checkbox.isEnabled()){
            AsyncMapStep asyncMapStep = new AsyncMapStep(id, myPos, true);
        }else{
            AsyncMapStep asyncMapStep = new AsyncMapStep(id, myPos, false);
        }

    }


}
