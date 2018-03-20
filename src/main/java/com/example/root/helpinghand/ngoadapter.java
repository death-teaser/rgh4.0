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

public class ngoadapter extends BaseAdapter {
    Context context;
    ArrayList<ngolog> donationList;
    public ngoadapter(Context ngoprofile, ArrayList<ngolog> donationList) {
        this.context = ngoprofile;
        this.donationList = donationList;
    }

    @Override
    public int getCount() {
        return donationList.size();
    }

    @Override
    public ngolog getItem(int i) {
        return donationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = View.inflate(context, R.layout.ngo_adapter_layout,null);
        }
        ngolog donation = donationList.get(i);
        TextView tv = (TextView)view.findViewById(R.id.type);
        TextView status = (TextView)view.findViewById(R.id.status);
        TextView phone = (TextView)view.findViewById(R.id.Mobile);
        TextView receiver =(TextView)view.findViewById(R.id.reciever);
        TextView date = (TextView)view.findViewById(R.id.date);
        TextView from = (TextView)view.findViewById(R.id.from);
        tv.setText(donation.getType());
        status.setText(donation.getStatus());
        phone.setText(donation.getMobile());
        receiver.setText(donation.getReceiver());
        date.setText(donation.getDate());
        from.setText(donation.getFrom());
        return view;
    }
}
