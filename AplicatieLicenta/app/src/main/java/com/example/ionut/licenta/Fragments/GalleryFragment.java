package com.example.ionut.licenta.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ionut.licenta.Adapters.Pictura_Adapter;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.Classes.Pictura;

import com.example.ionut.licenta.OnFragmentInteractionListener;
import com.example.ionut.licenta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment implements OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private ProgressDialog pDialog;
    public static final String url_picturi = "http://artpictures.net84.net/picturi.php";
    private ListView listView;
    public static ArrayList<Pictura> picturi = new ArrayList<>();
    public Pictura_Adapter adapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        listView = (ListView) v.findViewById(R.id.listView_galerie);
        adapter = new Pictura_Adapter(getActivity(), picturi);
        listView.setAdapter(adapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading message...");
        pDialog.setTitle("Loading title...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        makeJsonObjectRequest();
    }

    private void makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_picturi, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("majeJsonObjectRequest", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("pictures");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Pictura pictura = new Pictura();
                                JSONObject object = (JSONObject) jsonArray.get(i);
                                pictura.setID(Integer.parseInt(object.getString("Id")));
                                pictura.setNume(object.getString("nume"));
                                pictura.setPictor(object.getString("pictor"));
                                pictura.setSrc(object.getString("src"));
                                pictura.setDescriere(object.getString("description"));
                                pictura.setLikes(Integer.parseInt(object.getString("likes")));
                                picturi.add(pictura);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        hidepDialog();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onErrorResponse", "Error: " + error.getMessage());
                hidepDialog();
            }
        }
        );
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
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

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
