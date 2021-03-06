package com.afomic.spark.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.spark.R;


/**
 * Created by afomic on 30-Nov-16.
 */
public class navAdapter extends BaseAdapter {
    Context context;
    String[] navigation={"Blog","Time Table","Profile","Constitution","Gypee","Course List","FeedBack","Help","Settings"};
    int[] imageId={R.drawable.feedback,R.drawable.feedback,R.drawable.home,R.drawable.help,R.drawable.feedback,R.drawable.feedback,R.drawable.feedback,R.drawable.feedback,R.drawable.about};
    public navAdapter(Context c){
        context=c;
    }
    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public String getItem(int position) {
        return navigation[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(R.layout.nav_item,parent,false);
        TextView navText=(TextView) v.findViewById(R.id.nav_title);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"font/Lato-Regular.ttf");
        ImageView navIcon=(ImageView) v.findViewById(R.id.nav_icon);
        if(position==0){
            View indication =v.findViewById(R.id.nav_indicator);
            indication.setBackgroundColor(Color.argb(255,3, 169,244));
            navIcon.setImageResource(R.drawable.home_click);
            navText.setText(getItem(position));
            navText.setTextColor(Color.argb(255,3, 169,244));
            return v;
        }
        navText.setText(getItem(position));
        navText.setTypeface(typeface);
        navIcon.setImageResource(imageId[position]);
        return v;
    }
}
