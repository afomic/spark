package com.afomic.spark.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afomic.spark.R;


/**
 * Created by afomic on 04-Dec-16.
 */

public class ColorPickerAdapter extends BaseAdapter {
    int[] colors={0,Color.rgb(244,67,54),Color.rgb(121,85,72),Color.rgb(156,39,176),Color.rgb(96,125,139),
            Color.rgb(0,188,212),Color.rgb(255,193,7),Color.rgb(254,121,48),Color.rgb(254,48,101),Color.rgb(0,77,64),
            Color.rgb(83,83,83),Color.rgb(34,120,161)};
    Context context;
    public ColorPickerAdapter(Context c){
        context=c;
    }
    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.color_item,parent,false);
        TextView color=(TextView) convertView.findViewById(R.id.background_color);
        if(position==0){
            color.setText("Set Background");
        }else {
            color.setBackgroundColor(colors[position]);
        }
        return convertView;
    }
}
