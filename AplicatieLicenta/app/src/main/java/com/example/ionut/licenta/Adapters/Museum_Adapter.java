package com.example.ionut.licenta.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.Classes.Museum;
import com.example.ionut.licenta.R;


import java.util.ArrayList;

/**
 * Created by Ionut on 5/5/2015.
 */
public class Museum_Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private final ArrayList<Museum> itemsArrayList;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public Museum_Adapter(Activity activity, ArrayList<Museum> itemsArrayList) {
        this.activity = activity;
        this.itemsArrayList = itemsArrayList;



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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.museum_list_view, null);
        }
        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
        }


        TextView tv_museum_name = (TextView) convertView.findViewById(R.id.textView);
        NetworkImageView image = (NetworkImageView) convertView.findViewById(R.id.image_museum);
        tv_museum_name.setText(itemsArrayList.get(position).getNume());
        image.setImageUrl(itemsArrayList.get(position).getImage_src(), imageLoader);



        return convertView;
    }


}
