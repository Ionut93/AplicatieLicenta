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
        tv_descripition.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer augue nisi, rhoncus at ultrices in, rutrum ut risus. Etiam ut accumsan augue, et aliquam lectus. Mauris ac luctus tortor. Nulla sit amet purus vulputate, fermentum nunc sed, porta lorem. Nullam lobortis enim non arcu rhoncus tincidunt. Vestibulum quis eros quis diam suscipit facilisis. Sed hendrerit, purus at accumsan efficitur, enim diam facilisis tellus, vitae tincidunt tortor massa eu ante. Suspendisse pellentesque lectus sed ipsum commodo, in vehicula augue auctor. Proin at ex commodo, condimentum nunc sed, accumsan quam.\n" +
                "\n" +
                "In erat dui, laoreet nec mattis eu, auctor placerat magna. Fusce imperdiet, nibh non semper tempor, dolor ante porttitor libero, nec interdum mi massa sed eros. Proin malesuada dapibus dui vel posuere. Donec pulvinar ligula sit amet ligula viverra blandit. Fusce mattis molestie urna ut volutpat. Integer nec tempor turpis. Sed sodales sodales tellus vel tincidunt. Quisque molestie urna eget mauris vulputate porttitor. Integer elementum eget tellus blandit aliquet. Integer eu libero ac orci euismod interdum ut quis dolor. Mauris ex lorem, pharetra ut velit sit amet, placerat ultricies ex. Vivamus ultrices tincidunt sem, ac egestas nunc consequat rutrum.\n" +
                "\n" +
                "Praesent in ligula feugiat, varius lorem non, imperdiet nunc. Quisque sagittis viverra elementum. Suspendisse non ipsum ac augue congue aliquam vel vel dui. Proin varius dui nec venenatis tincidunt. Cras ultrices luctus varius. Pellentesque tincidunt tellus eu enim sodales gravida. Proin purus lorem, faucibus ac vestibulum id, vehicula nec eros.\n" +
                "\n" +
                "Phasellus congue odio non quam rutrum sagittis. Phasellus malesuada arcu eget lorem lacinia porttitor. Maecenas dapibus iaculis arcu non consequat. Etiam faucibus leo pharetra mauris fringilla varius. Proin laoreet vulputate velit, a imperdiet nisi aliquam a. Vivamus ac neque cursus, convallis quam at, tempor odio. Curabitur eu ultrices nunc. Interdum et malesuada fames ac ante ipsum primis in faucibus. Proin blandit, augue nec posuere bibendum, felis odio auctor tellus, non consectetur ante tortor vestibulum tortor. Morbi gravida pulvinar purus nec pretium. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n" +
                "\n" +
                "Nam quis dui condimentum, pretium sem sit amet, pellentesque elit. Sed mattis lorem in mauris ultrices, id bibendum dui varius. Donec augue libero, ullamcorper semper ante quis, viverra lacinia sem. Praesent in risus scelerisque, elementum ante ac, placerat turpis. Aliquam quis quam rhoncus, porttitor justo non, ultricies ante. Suspendisse consequat fermentum ante, ac accumsan erat eleifend quis. Praesent varius tincidunt porta. Donec id varius purus.");

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
}
