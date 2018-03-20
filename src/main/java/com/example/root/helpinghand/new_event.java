package com.example.root.helpinghand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class new_event extends AppCompatActivity {
    String email;
    EditText Event_title,Event_desc,Event_date,Event_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("details");

    }
    public void oncreate(View v){
        Event_title=(EditText)findViewById(R.id.Event_title);
        Event_desc=(EditText)findViewById(R.id.Event_desc);
        Event_date = (EditText)findViewById(R.id.Event_date);
        Event_location=(EditText)findViewById(R.id.Event_location);

        String desc = Event_desc.getText().toString();
        desc = desc.replace(' ','+');
        Log.d("description",desc);
        String dat=Event_date.getText().toString();
        dat=dat.replace(' ','+');
        String title = Event_title.getText().toString();
        title = title.replace(' ','+');
        String location=Event_location.getText().toString();
        location = title.replace(' ','+');
        if(v.getId()== R.id.Event_button){
            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.43.193:8000/events?Event_title="+title+"&Event_location="+location+"&Event_date="+dat+"&Event_desc="+desc+"&user_email="+email, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(new_event.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(new_event.this, "Internet failure", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsonrequest);
        }
    }
}
