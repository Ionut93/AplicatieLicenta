package com.example.ionut.licenta.Activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ionut.licenta.Data.ProfilePictureView;
import com.example.ionut.licenta.Fragments.ArtistsFragment;
import com.example.ionut.licenta.Fragments.MuseumFragment;
import com.example.ionut.licenta.Fragments.NavigationDrawerFragment;
import com.example.ionut.licenta.Fragments.SettingsFragment;
import com.example.ionut.licenta.OnFragmentInteractionListener;
import com.example.ionut.licenta.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;


public class MenuActivity extends ActionBarActivity implements OnFragmentInteractionListener {


    private String user_id;
    private Toolbar toolbar;
    private NavigationDrawerFragment drawerFragment;
    private ListView lv;


    private UiLifecycleHelper uiHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if (session.isClosed()) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        uiHelper.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
      //  Intent i = getIntent();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        drawerFragment.setUp(R.id.navigation_drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

     //   user_id = i.getStringExtra("user_id");
        final ProfilePictureView profilePictureView = (ProfilePictureView)drawerFragment.getView().findViewById(R.id.profile_picture);

        final Session session = Session.getActiveSession();
        if(session != null && session.isOpened()) {
            Request request = Request.newMeRequest(session , new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if(session == Session.getActiveSession()) {
                        if(user != null) {
                            user_id = user.getId();
                            profilePictureView.setProfileId(user_id);
                        }
                    }
                }
            });
            Request.executeBatchAsync(request);
        }



        lv = (ListView) drawerFragment.getView().findViewById(R.id.menu_list_view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        Fragment museumFragment = new MuseumFragment();
                        fragmentTransaction.replace(R.id.frame_layout, museumFragment);
                        drawerFragment.getmDrawerLayout().closeDrawers();
                        break;
                    case 1:
                        Fragment artistsFragment = new ArtistsFragment();
                        fragmentTransaction.replace(R.id.frame_layout, artistsFragment);
                        drawerFragment.getmDrawerLayout().closeDrawers();
                        break;
                    case 2:
                        Fragment settingsFragment = new SettingsFragment();
                        fragmentTransaction.replace(R.id.frame_layout, settingsFragment);
                        drawerFragment.getmDrawerLayout().closeDrawers();
                        break;
                    case 3:
                        Toast.makeText(getApplication(), "3", Toast.LENGTH_LONG).show();
                        drawerFragment.getmDrawerLayout().closeDrawers();
                        break;
                    case 4:
                        Session.getActiveSession().closeAndClearTokenInformation();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        break;
                }
                fragmentTransaction.commit();

            }
        });



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
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
    public void onFragmentInteraction(Uri uri) {

    }
}
