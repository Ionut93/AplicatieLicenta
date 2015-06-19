package com.example.ionut.licenta.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.*;

import com.example.ionut.licenta.R;
import com.facebook.widget.LoginButton;


/**
 * Created by Ionut on 2/17/2015.
 */
public class MainFragment extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.fb_login_button);
        authButton.setFragment(this);
        authButton.setReadPermissions("user_location","user_birthday");
        return view;
    }
}
