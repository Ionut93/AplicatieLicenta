package com.example.ionut.licenta.Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ionut.licenta.Data.ColorTool;
import com.example.ionut.licenta.OnFragmentInteractionListener;
import com.example.ionut.licenta.R;


public class MenuFragment extends Fragment implements OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView iv;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.menu_fragemtn_layout, container, false);
        iv = (ImageView) v.findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.room);
        if( iv != null)
            iv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int action = event.getAction();
                    final int evX = (int) event.getX();
                    final int evY = (int) event.getY();
                    if (action == MotionEvent.ACTION_UP) {
                        int touchColor = getHotSpotColor(R.id.imageView, evX, evY);
                        ColorTool ct = new ColorTool();
                        int tolerance = 25;
                        if (ct.closeMatch(Color.GREEN, touchColor, tolerance)) {
                            Toast.makeText(getActivity(), "Room1", Toast.LENGTH_LONG).show();
                        } else if (ct.closeMatch(Color.RED, touchColor, tolerance)) {
                            Toast.makeText(getActivity(), "Room2", Toast.LENGTH_LONG).show();
                        } else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
                            Toast.makeText(getActivity(), "Room3", Toast.LENGTH_LONG).show();
                        }
                    }
                    return true;
                }
            });
        return v;
    }

    public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();
        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        if (action == MotionEvent.ACTION_UP) {
            int touchColor = getHotSpotColor(R.id.imageView, evX, evY);
            ColorTool ct = new ColorTool();
            int tolerance = 25;
            if (ct.closeMatch(Color.GREEN, touchColor, tolerance)) {
                Toast.makeText(getActivity(), "Room1", Toast.LENGTH_LONG).show();
            } else if (ct.closeMatch(Color.RED, touchColor, tolerance)) {
                Toast.makeText(getActivity(), "Room2", Toast.LENGTH_LONG).show();
            } else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
                Toast.makeText(getActivity(), "Room3", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    public int getHotSpotColor(int hotspotId, int x, int y) {
        iv.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(iv.getDrawingCache());
        iv.setDrawingCacheEnabled(false);
        return bitmap.getPixel(x, y);
    }

    public boolean closeMath(int color1, int color2, int tolerance) {
        if ((int) Math.abs(Color.red(color1) - Color.red(color2)) > tolerance)
            return false;
        if ((int) Math.abs(Color.green(color1) - Color.green(color2)) > tolerance)
            return false;
        if ((int) Math.abs(Color.blue(color1) - Color.blue(color2)) > tolerance)
            return false;
        return true;
    }


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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
