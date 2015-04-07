package connectus.connectus;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by egiurleo on 4/5/15.
 */
public class ConnectUSActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_notifications:
                notificationClick();
                return true;
            case R.id.action_profile:
                myProfileClick();
                return true;
            case R.id.action_friends:
                friendsListClick(null);
                return true;
            case R.id.action_logout:
                logoutClick();
                return true;
            default:
                return false;
        }
    }

    public void logoutClick() {
        Log.e("loggingOut", "loggedout");
        //LoginManager.getInstance().logOut();

        Intent logoutIntent = new Intent(this, GoogleLoginActivity.class);
        startActivity(logoutIntent);
    }
    public void myProfileClick(){
        Intent myProfileIntent = new Intent(ConnectUSActivity.this, MyProfileActivity.class);
        //myProfileIntent.putExtra("user", username);     //Pass username to next activity
        ConnectUSActivity.this.startActivity(myProfileIntent);
    }

    public void friendsListClick(View view){
        Intent friendsListIntent = new Intent(ConnectUSActivity.this, FriendsListActivity.class);
        //friendsListIntent.putExtra("user", username);

        ConnectUSActivity.this.startActivity(friendsListIntent);
    }

    public void notificationClick(){
        Intent notificationIntent = new Intent(ConnectUSActivity.this, NotificationsActivity.class);
        //notificationIntent.putExtra("user", username);

        ConnectUSActivity.this.startActivity(notificationIntent);
    }
}
