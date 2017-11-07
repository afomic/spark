package com.afomic.spark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.spark.R;


/**
 * Created by afomic on 27-Dec-16.
 */

public class FeedBackAdapter extends BaseAdapter {
    String[] feeds={"Like us Facebook","Follow us on Instagram","Email us at oaunacoss@gmail.com"};
    int[] iconId={R.drawable.facebook,R.drawable.instagram,R.drawable.msg};
    Context context;
    public FeedBackAdapter(Context c){
        context=c;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.feedback_item,viewGroup,false);
        View div=view.findViewById(R.id.feedback_div);
        ImageView image=(ImageView) view.findViewById(R.id.feedback_icon);
        TextView feedback=(TextView) view.findViewById(R.id.feedback_text);
        image.setImageResource(iconId[i]);
        feedback.setText(feeds[i]);
        if(i==2){
            div.setVisibility(View.GONE);
        }

        return view;
    }
}
