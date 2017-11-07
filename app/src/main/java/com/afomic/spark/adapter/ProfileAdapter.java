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
 * Created by afomic on 13-Jan-17.
 */

public class ProfileAdapter extends BaseAdapter {
    String[] options={"Executives","Parliamentarian","Lecturers"};
    int[] imageID={R.drawable.exco_icon, R.drawable.pa,R.drawable.lecturer_icon};
    Context context;
    public ProfileAdapter(Context c) {
        context=c;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public String getItem(int i) {
        return options[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ProfileHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.profile_card,viewGroup,false);
            holder=new ProfileHolder();
            holder.sectionName=(TextView) view.findViewById(R.id.profile_text);
            holder.sectionImage=(ImageView) view.findViewById(R.id.profile_image);
            view.setTag(holder);
        }else {
            holder=(ProfileHolder) view.getTag();
        }
        holder.sectionImage.setImageResource(imageID[i]);
        holder.sectionName.setText(getItem(i));
        return  view;
    }
    public class ProfileHolder{
        TextView sectionName;
        ImageView sectionImage;
    }
}
