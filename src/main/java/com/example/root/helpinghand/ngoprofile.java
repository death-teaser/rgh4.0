package com.example.root.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ngoprofile extends AppCompatActivity {
    JSONObject details,notification;
    String email,type;
    ArrayList<ngolog> donationList;
    ngoadapter np;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngoprofile);
        try {
            details = new JSONObject(getIntent().getStringExtra("details"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            email = details.getString("user_email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        donationList = new ArrayList<>();
        lv = (ListView)findViewById(R.id.list_ngo);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.43.193:8000/Notifications?user_email="+email, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                notification = response;
//                Toast.makeText(ngoprofile.this, response.toString(), Toast.LENGTH_SHORT).show();
                try{
                    JSONArray don_list = response.getJSONArray("donation_log");
                    for (int i = 0; i < don_list.length(); i++) {
                        JSONObject x = don_list.getJSONObject(i);
                        String name = x.getString("receiver");
                        if(x.getString("type").equals("1")){
                            type = "Money";
                        }
                        else if (x.getString("type").equals("2")){
                            type = "Books";
                        }
                        else if (x.getString("type").equals("3")){
                            type = "Clothes";
                        }
                        else
                            type = "Others";
                        String status = x.getString("status");
                        String phone = x.getString("phone");
                        String date = x.getString("date");
                        String from = x.getString("from");
                        int id = x.getInt("id");
                        ngolog don = new ngolog();
                        don.setType(type);
                        don.setFrom(from);
                        don.setStatus(status);
                        don.setDate(date);
                        don.setMobile(phone);
                        don.setReceiver(name);
                        don.setId(id);
                        donationList.add(don);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                np = new ngoadapter(ngoprofile.this,donationList);
                lv.setAdapter(np);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ngolog donation = donationList.get(i);
                        Intent in = new Intent(ngoprofile.this,recieverman.class);
                        in.putExtra("details",donation);
                        startActivity(in);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ngoprofile.this, "Internet Failure", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonrequest);
    }
    public void onbutton(View v){
        if(v.getId() == R.id.nfab ){
            Intent i = new Intent(this, new_event.class);
            i.putExtra("details",email);
            startActivity(i);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.yourentry, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, Events.class);
        JSONArray eventList = null;
        try {
            eventList = notification.getJSONArray("event_list");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        i.putExtra("eventList",eventList.toString());
        startActivity(i);
        return true;
    }
}
