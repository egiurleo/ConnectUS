package connectus.connectus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Jon M Beaulieu Jr on 3/27/2015.
 */
public class RoadmapActivity extends ConnectUSActivity {
    public int mapPos;
    private String id;
    private OnMenuItemClickListener userProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.roadmap);

        Intent intent = getIntent();
        id = intent.getStringExtra("userId");

        //have the file cache
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        ConnectivityManager cm =
                                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        boolean isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();

                        if (isConnected) {
                            AsyncLogin asyncLogin = new AsyncLogin(getApplicationContext());
                            asyncLogin.execute(id);

                            AsyncSendLogToServer asyncSendLogToServer = new AsyncSendLogToServer(getApplicationContext(), id);
                            asyncSendLogToServer.execute();
                            Log.e("Hello", "doing the thing");

                        }
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 0, 120000); //execute in every 2 mins

        System.out.println("doing asyncroadmap again");

        AsyncRoadmap roadmapBackground = new AsyncRoadmap(getApplicationContext(), RoadmapActivity.this, this);
        roadmapBackground.execute();

        LogTest asyncLogTest = new LogTest(getApplicationContext(), RoadmapActivity.this, this, id);
        asyncLogTest.execute();
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

    public void popupMenu(View view){
        String[] list = ((String)view.getTag()).split("\\|");

        //create popup
        PopupMenu popup = new PopupMenu(this, view);


        //create menu
        Menu menu = popup.getMenu();
        for(String listItem : list){
            if(!listItem.equals("")) {
                String[] idName = listItem.split(",");
                menu.add(idName[1]);
            }
        }

        popup.show();
    }

    @Override
    public void onBackPressed(){
        //LEAVE THIS HERE
    }

    @Override
    public void onRestart(){
        System.out.println("restarting!");
        super.onRestart();
        AsyncRoadmap roadmapBackground = new AsyncRoadmap(getApplicationContext(), RoadmapActivity.this, this);
        roadmapBackground.execute();
    }


}