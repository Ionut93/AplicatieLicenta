package com.example.ionut.licenta.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ionut.licenta.Activities.ArtistViewActivity;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.Data.Arist_Adapter;
import com.example.ionut.licenta.Data.Artist;
import com.example.ionut.licenta.OnFragmentInteractionListener;
import com.example.ionut.licenta.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ArtistsFragment extends Fragment implements OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lv;
    private ProgressDialog pDialog;
    private Arist_Adapter adapter;
    private ArrayList<Artist> artists = new ArrayList<>();
    private static ArrayList<Bitmap> bitmaps = new ArrayList<>();
    public static final String url_artist = "http://artpictures.net84.net/artists.php";


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistsFragment newInstance(String param1, String param2) {
        ArtistsFragment fragment = new ArtistsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_artists, container, false);
        lv = (ListView) v.findViewById(R.id.listView_artists);
        adapter = new Arist_Adapter(artists,getActivity());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ArtistViewActivity.class);
                i.putExtra("artist_src",artists.get(position).getUrl());
                i.putExtra("artist_description",artists.get(position).getDescriere());
                i.putExtra("artist_name",artists.get(position).getNume());
                startActivity(i);
            }
        });
        return  v;
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

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p/>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */
    }

    private void makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_artist, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("majeJsonObjectRequest", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("pictures");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Artist artist = new Artist();
                                JSONObject object = (JSONObject) jsonArray.get(i);
                                artist.setNume(object.getString("Artist_name"));
                                artist.setUrl(object.getString("Artist_src"));
                                artist.setDescriere(object.getString("Artist_description"));
                                artists.add(artist);
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
    public void onFragmentInteraction(Uri uri) {

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
