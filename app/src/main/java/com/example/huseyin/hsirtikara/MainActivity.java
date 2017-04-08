package com.example.huseyin.hsirtikara;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    JSONArray venueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        ListView lv = (ListView) findViewById(R.id.lv_venues);
//
//        String[] data = {"Mekan1", "Mekan2","Mekan3"};
//
//        VenuesArrayAdapter adapter = new VenuesArrayAdapter(this,data);
//
//        lv.setAdapter(adapter);


        getVenues();




    }


    public void getVenues()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.foursquare.com/v2/venues/search?oauth_token=LYO13YYTJBNH5VAHI2BQJLX2SLMVIXTMF13ESE1HBYMV2TU4&v=20131016&ll=41.091379%2C%2029.091870&intent=checkin";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        processData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void processData(String response)
    {

        try {
            JSONObject responseObject = new JSONObject(response);
            venueList = responseObject.getJSONObject("response").getJSONArray("venues");
            showData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void showData()
    {
        ListView lv = (ListView) findViewById(R.id.lv_venues);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(MainActivity.this,VenueMapsActivity.class);
                startActivity(i);

            }
        });


        VenuesArrayAdapter adapter = new VenuesArrayAdapter(this,new String[venueList.length()]);

        lv.setAdapter(adapter);





    }

    public class VenuesArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public VenuesArrayAdapter(Context context, String[] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.venuelayout, parent, false);

            TextView txtVenue = (TextView) rowView.findViewById(R.id.txt_venue);


            try {

                txtVenue.setText(venueList.getJSONObject(position).getString("name"));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return rowView;
        }
    }



}








