package com.example.ionut.licenta.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.Classes.Facts;
import com.example.ionut.licenta.Classes.Museum;
import com.example.ionut.licenta.Data.MyReceiver;
import com.example.ionut.licenta.R;
import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class MainActivity extends Activity {

   // private MainFragment mainFragment;
    private PendingIntent pendingIntent;
    private ProgressDialog pDialog;
    public static int factNumber;
    public static ArrayList<Facts> facts = new ArrayList<>();
    private String url = "http://artpictures.net84.net/facts.php";
    public static int min = 0;
    public static int max = 5;
    private String user_id;
    private TextView didyouknow;
    private ImageView imageView;
    private LoginButton fb_button;
    private Context context;
    private Button button;
    Calendar calendar;
    public static Activity main;
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
        main = this;
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        Random r = new Random();
        factNumber = r.nextInt(max - min + 1) + min;
        didyouknow = (TextView) findViewById(R.id.textView5);
        imageView = (ImageView)findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.ic_launcher);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading message...");
        pDialog.setTitle("Loading title...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        makeJsonObjectRequest();
        context = this;
        fb_button = (LoginButton) findViewById(R.id.fb_login_button);
        fb_button.setReadPermissions("email", "basic_info");
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 5);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);
        Session session = Session.getActiveSession();
        if(session.isOpened()) {
            Log.i("Facebook", "Logged in...");
            Intent intent = new Intent(context, MenuActivity.class);
   //         intent.putExtra("user_id",user_id);
            startActivity(intent);
            finish();
        }
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("majeJsonObjectRequest", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("facts");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = (JSONObject) jsonArray.get(i);
                                Facts f = new Facts();
                                f.setId(Integer.parseInt(object.getString("Id")));
                                f.setFact(object.getString("fact"));
                                facts.add(f);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                         hidepDialog();
                        Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);
                        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                        didyouknow.setText(facts.get(factNumber).getFact());
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onErrorResponse", "Error: " + error.getMessage());
                didyouknow.setText("There are some problems with the internet connection");
                hidepDialog();
            }
        }
        );
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
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

