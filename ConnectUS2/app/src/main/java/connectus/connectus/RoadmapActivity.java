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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.roadmap);

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
        System.out.println(mapPos);
        Intent mapStepIntent = new Intent(RoadmapActivity.this, MapStepActivity.class);
        mapStepIntent.putExtra("buttonID", getResources().getResourceEntryName(view.getId()));
        mapStepIntent.putExtra("mapPos", mapPos);
        RoadmapActivity.this.startActivity(mapStepIntent);
    }


}