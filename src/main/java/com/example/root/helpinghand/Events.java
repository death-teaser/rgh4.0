package com.example.root.helpinghand;

import android.app.usage.UsageEvents;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Events extends AppCompatActivity {
    JSONArray EventList;
    ArrayList<Eventlog> eventList;
    ListView lv;
    EventAdapter ea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        eventList = new ArrayList<>();
        lv = (ListView)findViewById(R.id.event_list);
        try {
            EventList = new JSONArray(getIntent().getStringExtra("eventList"));
            for(int i =0;i<EventList.length();i++){
                JSONObject x = EventList.getJSONObject(i);
                String title = x.getString("title");
                String org = x.getString("org");
                String desc = x.getString("desc");
                String date = x.getString("date");
                String location = x.getString("location");
                Eventlog event = new Eventlog();
                event.setDate(date);
                event.setLocation(location);
                event.setDesc(desc);
                event.setTitle(title);
                event.setOrga(org);
                eventList.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ea = new EventAdapter(Events.this,eventList);
        lv.setAdapter(ea);
    }
}
