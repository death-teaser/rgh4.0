package com.example.root.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class newdonation extends AppCompatActivity {
    JSONObject details;
    Spinner donationtype, donationto;
    String don_type, don_to,email;
    public String[] item2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdonation);
        try {
            details = new JSONObject(getIntent().getStringExtra("details"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        donationtype = (Spinner)findViewById(R.id.donationtype);
        donationto = (Spinner)findViewById(R.id.donation_to);
        try {
            email = details.getString("user_email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] items = new String[]{"Money", "Food", "Books","Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        donationtype.setAdapter(adapter);
        try {
            JSONArray itemlist = details.getJSONArray("ngo_list");
            item2 = new String[itemlist.length()+1];
            for(int i =0;i<itemlist.length();i++){
                JSONObject element = itemlist.getJSONObject(i);
                 item2[i] = element.getString("Username");
            }
            item2[itemlist.length()] = "NotifyAll";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, item2);
        donationto.setAdapter(adapter2);
        donationtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                don_type = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                don_type = null;
            }
        });
        donationto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                don_to = parent.getItemAtPosition(position).toString(); //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                don_to = null;
            }
        });
    }
    public void ondonate( View v){
        EditText donation_amount, donation_disc;
        donation_amount = (EditText)findViewById(R.id.donation_amount);
        String don_am = donation_amount.getText().toString();
        donation_disc = (EditText)findViewById(R.id.donation_desc);
        String don_disc = donation_disc.getText().toString();
        don_disc = don_disc.replace(' ','+');
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.43.193:8000/donate?donation_type="+don_type+"&donation_amount="+don_am+"&donation_to="+don_to+"&donation_description="+don_disc+"&user_email="+email, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(newdonation.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(newdonation.this, "Internet failure", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonrequest);
    }



}
