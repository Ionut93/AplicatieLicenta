package com.example.ionut.licenta.Data;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.R;


import java.util.ArrayList;

/**
 * Created by Ionut on 6/18/2015.
 */
public class Arist_Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private final ArrayList<Artist> itemsArrayList;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public Arist_Adapter(ArrayList<Artist> itemsArrayList, Activity activity) {

        this.itemsArrayList = itemsArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return itemsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.artist_layout, null);
        }
        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
        }

        TextView tv = (TextView) convertView.findViewById(R.id.TV_Artist_Description);
        NetworkImageView niv = (NetworkImageView) convertView.findViewById(R.id.NIV_artist);
        tv.setText(itemsArrayList.get(position).getDescriere());
        niv.setImageUrl(itemsArrayList.get(position).getUrl(), imageLoader);
        tv.setMovementMethod(new ScrollingMovementMethod());
        return convertView;
    }
}
