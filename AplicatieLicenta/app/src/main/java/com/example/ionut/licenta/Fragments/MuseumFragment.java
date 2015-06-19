package com.example.ionut.licenta.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.ionut.licenta.Activities.MuseumViewActivity;
import com.example.ionut.licenta.AppController;
import com.example.ionut.licenta.Data.Museum;
import com.example.ionut.licenta.Data.Museum_Adapter;
import com.example.ionut.licenta.OnFragmentInteractionListener;
import com.example.ionut.licenta.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MuseumFragment extends Fragment implements OnFragmentInteractionListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private ProgressDialog pDialog;
    private OnFragmentInteractionListener mListener;
    public static final String url_museums = "http://artpictures.net84.net/try.json";
    private ListView listView;
    private ArrayList<Museum> museums = new ArrayList<>();
    public Museum_Adapter adapter;


    public static MuseumFragment newInstance(String param1, String param2) {
        MuseumFragment fragment = new MuseumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MuseumFragment() {

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
        View v = inflater.inflate(R.layout.fragment_museum, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        adapter = new Museum_Adapter(getActivity(), museums);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MuseumViewActivity.class);
                i.putExtra("museum_src",museums.get(position).getImage_src());
                i.putExtra("museum_description",museums.get(position).getDescription());
                i.putExtra("museum_site",museums.get(position).getSite());
                startActivity(i);
            }
        });
        return v;
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


    private void makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_museums, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("majeJsonObjectRequest", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("pictures");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Museum museum = new Museum();
                                JSONObject object = (JSONObject) jsonArray.get(i);
                                museum.setNume(object.getString("Museum_name"));
                                museum.setImage_src(object.getString("Museum_src"));
                                museum.setDescription(object.getString("Description"));
                                museum.setSite(object.getString("Museum_site"));
                                museums.add(museum);
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
/*
    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = parser.getJSONFromURL(url_museums);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                JSONArray jArray = jsonObject.getJSONArray("pictures");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject obj = jArray.getJSONObject(i);
                    Museum m = new Museum();
                    m.setNume(obj.getString("Museum_name"));
                    m.setImage_src(obj.getString("Museum_src"));

                    museums.add(m);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();
        }
    }

    /*    JsonObjectRequest museumReq;      0724616787
        museumReq = new JsonObjectRequest(url_museums,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            Museum m = new Museum();

                            JSONArray jArray = response.getJSONArray("pictures");

                            m.setNume(jArray.getString(2));
                            m.setImage_src(jArray.getString(1));
                            museums.add(m);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }


                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley_Controller.getInstance().addToRequestQueue(museumReq);*/


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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



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
