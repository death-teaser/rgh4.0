package com.example.root.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    EditText user_name,user_fname,user_lname,user_email,user_password,user_gender,user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void onclick(View v){
        user_name = (EditText)findViewById(R.id.Username);
        String uname = user_name.getText().toString();
        user_fname = (EditText)findViewById(R.id.fname);
        String ufname = user_fname.getText().toString();
        user_lname = (EditText)findViewById(R.id.lname);
        String ulname = user_lname.getText().toString();
        user_email = (EditText)findViewById(R.id.email);
        String email = user_email.getText().toString();
        user_password = (EditText)findViewById(R.id.password);
        String pass = user_password.getText().toString();
        user_gender  =(EditText)findViewById(R.id.gender);
        String gender = user_gender.getText().toString();
        user_type  =(EditText)findViewById(R.id.user_type);
        String utype = user_type.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.43.193:8000/signup?user_name=" + uname +"&user_email="+email+"&user_fname=" + ufname + "&user_lname=" + ulname +"&user_gender=" + gender +"&user_password=" + pass+"&user_type="+utype, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("success").equals("True")){
                        Intent i =new Intent(SignUp.this, MainActivity.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(SignUp.this, "successfull"+response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUp.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonrequest);
    }
}
