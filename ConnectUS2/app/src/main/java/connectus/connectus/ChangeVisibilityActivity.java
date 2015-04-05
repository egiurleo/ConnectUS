package connectus.connectus;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.lang.Override;
import java.lang.reflect.Array;

public class ChangeVisibilityActivity extends ConnectUSActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.changevisibility);
    }

    private void setSpinners(){
//        int[] ids = {R.id.name_spinner, R.id.email_spinner, R.id.country_spinner, R.id.language_spinner, R.id.phone_spinner};
//
//        for(int id : ids){
//            Spinner spinner = (Spinner) findViewById(id);
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner.setAdapter(adapter);
//
//        }
    }

}