package com.example.root.helpinghand;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 3/10/17.
 */

public class donoradapter extends BaseAdapter {
    Context context;
    ArrayList<donationlog> donationList;
    public donoradapter(Context donorprofile, ArrayList<donationlog> donationList) {
        this.context = donorprofile;
        this.donationList = donationList;
    }

    @Override
    public int getCount() {
        return donationList.size();
    }

    @Override
    public donationlog getItem(int i) {
        return donationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = View.inflate(context, R.layout.donor_log,null);
        }
        donationlog donation = donationList.get(i);
        TextView tv = (TextView)view.findViewById(R.id.type);
        TextView status = (TextView)view.findViewById(R.id.status);
        TextView phone = (TextView)view.findViewById(R.id.Mobile);
        TextView receiver =(TextView)view.findViewById(R.id.reciever);
        TextView date = (TextView)view.findViewById(R.id.date);
        TextView org = (TextView)view.findViewById(R.id.to);
        tv.setText(donation.getType());
        status.setText(donation.getStatus());
        phone.setText(donation.getPhone());
        receiver.setText(donation.getReceiver());
        date.setText(donation.getDate());
        org.setText(donation.getOrg());
        return view;
    }
}
