package connectus.connectus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by Jon M Beaulieu Jr on 3/27/2015.
 */
public class RoadmapActivity extends ConnectUSActivity {
    public int mapPos;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.roadmap);

        Intent intent = getIntent();
        id = intent.getStringExtra("userId");

        System.out.println("doing asyncroadmap again");

        AsyncRoadmap roadmapBackground = new AsyncRoadmap(getApplicationContext(), RoadmapActivity.this, this);
        roadmapBackground.execute();

    }

    public void myProfileClick(View view){
        Intent myProfileIntent = new Intent(RoadmapActivity.this, MyProfileActivity.class);
        RoadmapActivity.this.startActivity(myProfileIntent);
    }

    public void friendProfileClick(View view){
        Intent otherProfileIntent = new Intent(RoadmapActivity.this, OtherProfileActivity.class);
        RoadmapActivity.this.startActivity(otherProfileIntent);
    }

    public void mapStepClick(View view){
        Intent mapStepIntent = new Intent(RoadmapActivity.this, MapStepActivity.class);
        mapStepIntent.putExtra("buttonID", getResources().getResourceEntryName(view.getId()));
        mapStepIntent.putExtra("mapPos", mapPos);
        mapStepIntent.putExtra("userId", id);
        RoadmapActivity.this.startActivityForResult(mapStepIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("getting result");
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                if (data.getStringExtra("result").equals("true")) {
                    System.out.println("this should work!");
                    AsyncRoadmap roadmapBackground = new AsyncRoadmap(getApplicationContext(), RoadmapActivity.this, this);
                    roadmapBackground.execute();
                }
            }
        }
    }


}