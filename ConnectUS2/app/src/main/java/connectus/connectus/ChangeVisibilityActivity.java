package connectus.connectus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class ChangeVisibilityActivity extends ConnectUSActivity {

    public String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //Hides Notification Bar

        setContentView(R.layout.changevisibility);

        AsyncChangeVisibility asyncChangeVisibility = new AsyncChangeVisibility(getApplicationContext(), ChangeVisibilityActivity.this, this);
        asyncChangeVisibility.execute();
    }

    public void done(View view){
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            AsyncDoneChanging asyncDoneChanging = new AsyncDoneChanging(getApplicationContext(), ChangeVisibilityActivity.this, id);
            asyncDoneChanging.execute();

            AsyncLogin asyncLogin = new AsyncLogin(getApplicationContext());
            asyncLogin.execute(id);
        }

        finish();
    }

    @Override
    public void onBackPressed(){
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            AsyncDoneChanging asyncDoneChanging = new AsyncDoneChanging(getApplicationContext(), ChangeVisibilityActivity.this, id);
            asyncDoneChanging.execute();

            AsyncLogin asyncLogin = new AsyncLogin(getApplicationContext());
            asyncLogin.execute(id);
        }

        finish();
    }


}