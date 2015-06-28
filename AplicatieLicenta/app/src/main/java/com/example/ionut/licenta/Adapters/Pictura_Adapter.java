package com.example.ionut.licenta.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.example.ionut.licenta.Activities.MenuActivity;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.Classes.Pictura;
import com.example.ionut.licenta.Data.CustomNetworkImageView;
import com.example.ionut.licenta.Fragments.GalleryFragment;
import com.example.ionut.licenta.R;

import java.util.ArrayList;


public class Pictura_Adapter extends BaseAdapter {

    private String query = "UPDATE  `a8229079_picsDB`.`Opere` SET  `likes` =  '1' WHERE  `Opere`.`Id` =1 ";
    private LayoutInflater inflater;
    CustomNetworkImageView image;
    private Activity activity;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ArrayList<Pictura> favPics = new ArrayList<>();
    private ToggleButton btn;


    public Pictura_Adapter(Activity activity, ArrayList<Pictura> favPics) {
        this.activity = activity;
        this.favPics = favPics;
    }

    @Override
    public int getCount() {
        return GalleryFragment.picturi.size();
    }

    @Override
    public Object getItem(int position) {
        return GalleryFragment.picturi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return GalleryFragment.picturi.get(position).getID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (position < favPics.size() && !favPics.isEmpty()) {
            if (inflater == null) {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gallery_layout, null);
            }
            if (imageLoader == null) {
                imageLoader = AppController.getInstance().getImageLoader();
            }


            TextView tv_museum_name = (TextView) convertView.findViewById(R.id.TV_Picture_Description);
            image = (CustomNetworkImageView) convertView.findViewById(R.id.NIV_gallery);
            btn = (ToggleButton) convertView.findViewById(R.id.button_like);
            if (favPics.get(position).isFavorite())
                btn.setChecked(true);
            else
                btn.setChecked(false);
            btn.setText(null);
            btn.setTextOn(null);
            btn.setTextOff(null);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!favPics.get(position).isFavorite()) {
                        favPics.get(position).setFavorite(true);

                        btn.setChecked(true);
                    } else {
                        btn.setChecked(false);
                        favPics.get(position).setFavorite(false);

                    }
                }
            });
            tv_museum_name.setText(GalleryFragment.picturi.get(position).getDescriere());
            image.setImageUrl(GalleryFragment.picturi.get(position).getSrc(), imageLoader);
        }
        return convertView;
    }
}
