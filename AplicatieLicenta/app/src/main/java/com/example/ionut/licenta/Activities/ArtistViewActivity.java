package com.example.ionut.licenta.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.Data.CustomNetworkImageView;
import com.example.ionut.licenta.R;
import com.facebook.LoginActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ArtistViewActivity extends ActionBarActivity {

    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private CustomNetworkImageView networkImageView;
    private TextView tv_descripition;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ImageButton button;
    private boolean pendingPublishReauthorization = false;
    private String desc;
    private String name;
    private Context context;
    private static boolean checked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_view);
        context = this;
        networkImageView = (CustomNetworkImageView) findViewById(R.id.view);
        tv_descripition = (TextView) findViewById(R.id.textView_artist);
        button = (ImageButton) findViewById(R.id.imageButton_share);
        button.setImageResource(R.drawable.share);
        tv_descripition.setMovementMethod(new ScrollingMovementMethod());
        Intent i = getIntent();
        desc = i.getStringExtra("artist_description");
        name = i.getStringExtra("artist_name");
        networkImageView.setImageUrl(i.getStringExtra("artist_src"), imageLoader);
        tv_descripition.setText(i.getStringExtra("artist_description"));
        imageLoader.get(i.getStringExtra("artist_src"), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    networkImageView.setLocalImageBitmap(response.getBitmap());
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session s = Session.getActiveSession();
                if (s != null && s.isOpened())
                    postImage();
                else {
                    Intent i = new Intent("facebook.LAUNCH");
                    startActivity(i);

                }
            }
        });

    }

    private boolean isSubsetOf(Collection<String> subset,
                               Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }

    public void check() {
        Session session = Session.getActiveSession();

        if (session != null) {

            // Check for publish permissions
            List<String> permissions = session.getPermissions();
            if (!isSubsetOf(PERMISSIONS, permissions)) {
                pendingPublishReauthorization = true;
                Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
                        this, PERMISSIONS);
                session.requestNewPublishPermissions(newPermissionsRequest);
                checked = true;
                return;
            }
        }
    }




    public void postImage() {
        if(!checked)
        check();
        Bitmap img = networkImageView.getmLocalBitmap();
        Request uploadRequest = Request.newUploadPhotoRequest(
                Session.getActiveSession(), img, new Request.Callback() {
                    @Override
                    public void onCompleted(Response response) {
                        Toast.makeText(getApplicationContext(),
                                "Photo uploaded successfully",
                                Toast.LENGTH_LONG).show();
                    }
                });
        Bundle params = uploadRequest.getParameters();
        params.putString("name",desc);
        uploadRequest.setParameters(params);
        uploadRequest.executeAsync();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_artist_view, menu);
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
}
