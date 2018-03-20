package com.example.root.helpinghand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class recieverman extends AppCompatActivity {
    int donation_id;
    EditText name,mobile,date;
    String donation_receiver,donation_mobile,donation_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieverman);
        final ngolog donation = (ngolog) getIntent().getSerializableExtra("details");
        donation_id = donation.getId();
    }
    public void onreceive(View v){

        name = (EditText)findViewById(R.id.donation_receiver);
        donation_receiver = name.getText().toString();
        mobile = (EditText)findViewById(R.id.donation_mobile);
        donation_mobile = mobile.getText().toString();
        date = (EditText)findViewById(R.id.donation_time);
        donation_time = date.getText().toString();
        donation_time = donation_time.replace(' ','+');
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.43.193:8000/donate_accept?donation_reciever="+donation_receiver+"&donation_mobile="+donation_mobile+"&donation_time="+donation_time+"&donation_id="+donation_id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(recieverman.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(recieverman.this, "Internet failure", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonrequest);
    }
}
