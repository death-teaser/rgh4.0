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

public class MainActivity extends AppCompatActivity {
    EditText user_email,user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onclick(View v){
        if(v.getId() == R.id.mlogin){
            user_email = (EditText)findViewById(R.id.email);
            String email = user_email.getText().toString();
            user_password = (EditText)findViewById(R.id.password);
            String pass = user_password.getText().toString();
            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.43.193:8000/login?user_email=" + email +"&password=" + pass, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String success = response.getString("success");
                        String a = "True";
                        if(success.equals(a)){
                            if(response.getString("user_type").equals("1")){
                                Intent i =new Intent(MainActivity.this, donorprofile.class);
                                i.putExtra("details",response.toString());
                                startActivity(i);
                            }
                            else{
                                Intent i =new Intent(MainActivity.this, ngoprofile.class);
                                i.putExtra("details",response.toString());
                                startActivity(i);
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "wrong credentials", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext(), "failure", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsonrequest);
        }
        if(v.getId() == R.id.signup){
            Intent i = new Intent(getBaseContext(),SignUp.class);
            startActivity(i);
        }
    }
}
