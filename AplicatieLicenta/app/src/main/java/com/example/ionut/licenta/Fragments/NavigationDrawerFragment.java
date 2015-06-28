package com.example.ionut.licenta.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ionut.licenta.OnFragmentInteractionListener;
import com.example.ionut.licenta.R;


public class NavigationDrawerFragment extends Fragment implements OnFragmentInteractionListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private ActionBarDrawerToggle mDrawerToggle;

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    private DrawerLayout mDrawerLayout;
    private View containerView;
    private ListView listView;


    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public static String SHARED_PREFERENCE_FILE_NAME = "spFile";
    private String M_USER_LEARNED_DRAWER_VALUE;


    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(gettPref(getActivity(),
                M_USER_LEARNED_DRAWER_VALUE, "false"));
        if (savedInstanceState != null)
            mFromSavedInstanceState = true;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        listView = (ListView) v.findViewById(R.id.menu_list_view);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Menu)));
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    setPref(getActivity(), M_USER_LEARNED_DRAWER_VALUE, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.4)
                    toolbar.setAlpha(1 - slideOffset);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static void setPref(Context context, String preferanceName, String preferanceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferanceName, preferanceValue);
        editor.apply();

    }

    public static String gettPref(Context context, String preferanceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferanceName, defaultValue);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
