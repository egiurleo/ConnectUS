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
    private String id;
    private boolean stuffChanged;

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
        id = intent.getStringExtra("userId");

        stuffChanged = false;

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
        if(mapPos > myPos) {
            checkbox.setChecked(true);
            checkbox.setEnabled(false);
        }else if(mapPos == myPos) {
            checkbox.setChecked(true);
        }else if(myPos == mapPos + 1){
            if(myPos == 6 && mapPos == 5) {
                checkbox.setEnabled(false);
            }
        }else if(mapPos < myPos - 1){
            if(myPos == 6 && mapPos == 4) {
                checkbox.setEnabled(true);
            }else{
                checkbox.setEnabled(false);
            }

        }

    }

    public void onCheckboxClick(View view){
        CheckBox checkbox = (CheckBox) findViewById(R.id.map_step_checkbox);

        AsyncMapStep asyncMapStep;

        if(checkbox.isChecked()){
            asyncMapStep = new AsyncMapStep(id, myPos, true, getApplicationContext(), MapStepActivity.this);
        }else{
            asyncMapStep = new AsyncMapStep(id, myPos, false, getApplicationContext(), MapStepActivity.this);
        }

        asyncMapStep.execute();

        if(stuffChanged){
            stuffChanged = false;
        }else{
            stuffChanged = true;
        }

    }

    @Override
    public void onBackPressed(){

        if(stuffChanged) {
            System.out.println("stuff changed!");

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "true");
            setResult(RESULT_OK, returnIntent);
            finish();
        }else{
            super.onBackPressed();
        }

    }


}
