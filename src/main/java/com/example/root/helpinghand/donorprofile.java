package com.example.root.helpinghand;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
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

public class donorprofile extends AppCompatActivity {
    private static  final int ra = 1;
    JSONObject details,notification;
    ImageView imv;
    ListView lv;
    donoradapter dp;
    String email,type;
    ArrayList<donationlog> donationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorprofile);
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
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_bar);
        lv = (ListView) findViewById(R.id.list_id);
        RequestQueue queue = Volley.newRequestQueue(this);
        donationList = new ArrayList<>();
        JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.43.193:8000/Notifications?user_email="+email, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                notification = response;
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
                        String org = x.getString("org");
                        donationlog don = new donationlog();
                        don.setType(type);
                        don.setOrg(org);
                        don.setStatus(status);
                        don.setDate(date);
                        don.setPhone(phone);
                        don.setReceiver(name);
                        donationList.add(don);
                    dp = new donoradapter(donorprofile.this, donationList);
                    lv.setAdapter(dp);
                   }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
           }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(donorprofile.this, "Internet Failure", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonrequest);

    }
    public void onupload(View v){
        if(v.getId() == R.id.button5) {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, ra);
        }
        if(v.getId() == R.id.button6){
            Toast.makeText(this, "Uploded", Toast.LENGTH_SHORT).show();
        }
        if(v.getId() == R.id.dfab){
            Intent i = new Intent(this, newdonation.class);
            i.putExtra("details", notification.toString());
            startActivity(i);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        imv = (ImageView)findViewById(R.id.imageView2);
        super.onActivityResult(requestCode, resultCode,data);
        if( requestCode == ra && data!=null && resultCode == RESULT_OK ){
            Uri selectedImage = data.getData();
            imv.setImageURI(selectedImage);
        }
    }
    @Override
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
