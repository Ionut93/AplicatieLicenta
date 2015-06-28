package com.example.ionut.licenta.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.Data.MyReceiver;
import com.example.ionut.licenta.R;


public class MuseumViewActivity extends ActionBarActivity {

    private NetworkImageView networkImageView;
    private TextView tv_site;
    private TextView tv_descripition;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private String web_site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_view);
        Intent i = getIntent();
        networkImageView = (NetworkImageView) findViewById(R.id.view);
        tv_site = (TextView) findViewById(R.id.textView2);
        tv_descripition = (TextView) findViewById(R.id.textView3);
        web_site = i.getStringExtra("museum_site");
        tv_descripition.setMovementMethod(new ScrollingMovementMethod());
        networkImageView.setImageUrl(i.getStringExtra("museum_src"), imageLoader);
        tv_descripition.setText(i.getStringExtra("museum_description"));
        tv_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!web_site.startsWith("http://") && !web_site.startsWith("https://"))
                    web_site = "http://" + web_site;
                Intent i = new Intent(getApplicationContext(),WebView_Activity.class);
                i.putExtra("web_site",web_site);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_museum_view, menu);
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
    protected void onDestroy() {
        super.onDestroy();

    }
}
