package com.example.root.helpinghand;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 1/11/17.
 */

public class EventAdapter extends BaseAdapter {
    Context context;
    ArrayList<Eventlog> eventList;

    public EventAdapter(Context events, ArrayList<Eventlog> eventList) {
        this.context=events;
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Eventlog getItem(int i) {
        return eventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = View.inflate(context, R.layout.event_adapter_layout,null);
        }
        Eventlog event = eventList.get(i);
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView location = (TextView)view.findViewById(R.id.location);
        TextView date = (TextView)view.findViewById(R.id.date);
        TextView desc =(TextView)view.findViewById(R.id.desc);
        TextView orga = (TextView)view.findViewById(R.id.orga);
        title.setText(event.getTitle());
        location.setText(event.getLocation());
        date.setText(event.getDate());
        desc.setText(event.getDesc());
        orga.setText(event.getOrga());
        return view;
    }
}
