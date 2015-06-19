package com.example.ionut.licenta.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.ionut.licenta.R;
import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;


public class MainActivity extends Activity {

   // private MainFragment mainFragment;
    private String user_id;
    private LoginButton fb_button;
    private Context context;
    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {

        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        context = this;
        fb_button = (LoginButton) findViewById(R.id.fb_login_button);
        fb_button.setReadPermissions("email","basic_info");
      /*  fb_button.setSessionStatusCallback(new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if(session.isOpened()) {
                    Request.newMeRequest(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if(user != null) {
                                user_id = user.getId();
                            }
                        }
                    }).executeAsync();
                }
            }
        });*/
        Session session = Session.getActiveSession();
        if(session.isOpened()) {
            Log.i("Facebook", "Logged in...");
            Intent intent = new Intent(context, MenuActivity.class);
   //         intent.putExtra("user_id",user_id);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if(session.isOpened()) {
            Log.i("Facebook", "Logged in...");
            Intent intent = new Intent(context, MenuActivity.class);
       //     intent.putExtra("user_id",user_id);
            startActivity(intent);
            finish();
        }
        else if(session.isClosed()) {
            Log.i("Facebook", "Logged out...");
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(context,String.valueOf(R.string.app_id));
        uiHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.activateApp(context,String.valueOf(R.string.app_id));
        uiHelper.onPause();
    }
}

