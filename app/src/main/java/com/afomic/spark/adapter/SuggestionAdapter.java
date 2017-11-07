package com.afomic.spark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.afomic.spark.R;
import com.afomic.spark.model.Course;

import java.util.ArrayList;

/**
 * Created by afomic on 03-Jun-16.
 *
 */
public class SuggestionAdapter extends ArrayAdapter{
    ArrayList<Course> courses=null;
    public SuggestionAdapter(Context context, int resource, ArrayList<Course> result) {
        super(context, resource);
        courses=result;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Course getItem(int position) {
        return courses.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course=getItem(position);
        ViewHolder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.course_search_item,parent,false);
            holder =new ViewHolder();
            holder.searchName=(TextView)convertView.findViewById(R.id.gypee_course_name);
            holder.searchTitle=(TextView)convertView.findViewById(R.id.gypee_course_title);
            convertView.setTag(holder);
        }
        else {
             holder=(ViewHolder) convertView.getTag();
        }
        holder.searchName.setText(course.getCourseName());
        holder.searchTitle.setText(course.getTitle());
        return convertView;
    }

    public class ViewHolder{
        TextView searchName,searchTitle;
    }

}
