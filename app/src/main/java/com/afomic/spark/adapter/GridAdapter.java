package com.afomic.spark.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;


import com.afomic.spark.R;
import com.afomic.spark.model.Course;

import java.util.ArrayList;

/**
 * Created by afomic on 08-Jul-16.
 */
public class GridAdapter extends BaseAdapter {
    ArrayList<Course> userCourse;
    int toggle=0;
    int[] color={Color.argb(150,190,210,220),Color.argb(150,225,245,254)};
    public GridAdapter(ArrayList<Course> arrayList){
        userCourse=arrayList;

    }
    @Override
    public int getCount() {
        return userCourse.size();
    }

    @Override
    public Course getItem(int position) {
        return userCourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course myCourse=userCourse.get(position);
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.score_card,parent,false);
        TextView name=(TextView)convertView.findViewById(R.id.t_name);
        RadioButton customA=(RadioButton) convertView.findViewById(R.id.rb_custom_A);
        RadioButton customB=(RadioButton) convertView.findViewById(R.id.rb_custom_B);
        RadioButton customC=(RadioButton) convertView.findViewById(R.id.rb_custom_C);
        RadioButton customD=(RadioButton) convertView.findViewById(R.id.rb_custom_D);
        RadioButton customE=(RadioButton) convertView.findViewById(R.id.rb_custom_E);
        RadioButton customF=(RadioButton) convertView.findViewById(R.id.rb_custom_F);
        name.setText(myCourse.getCourseName());

        customA.setOnCheckedChangeListener(new MyOnclick(myCourse));
        customB.setOnCheckedChangeListener(new MyOnclick(myCourse));
        customC.setOnCheckedChangeListener(new MyOnclick(myCourse));
        customD.setOnCheckedChangeListener(new MyOnclick(myCourse));
        customE.setOnCheckedChangeListener(new MyOnclick(myCourse));
        customF.setOnCheckedChangeListener(new MyOnclick(myCourse));
        if (myCourse.getResourceID() != 0) {
            RadioButton button=(RadioButton)convertView.findViewById(myCourse.getResourceID());
            button.setChecked(true);
        }
        return convertView;
    }


    public class MyOnclick implements CompoundButton.OnCheckedChangeListener{
        Course Course;

        public MyOnclick(Course course){
            Course=course;
        }
        @Override

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.rb_custom_A:

                       if(isChecked){
                           Course.setGrade(5);
                           Course.setResourceID(buttonView.getId());
                       }


                    break;
                case R.id.rb_custom_B:
                      if(isChecked){
                          Course.setGrade(4);
                          Course.setResourceID(buttonView.getId());
                      }

                    break;
                case R.id.rb_custom_C:
                    if(isChecked){
                        Course.setGrade(3);
                        Course.setResourceID(buttonView.getId());
                    }
                    break;
                case R.id.rb_custom_D:
                        if(isChecked){
                            Course.setGrade(2);
                            Course.setResourceID(buttonView.getId());
                        }


                    break;
                case R.id.rb_custom_E:
                        if(isChecked){
                            Course.setGrade(1);
                            Course.setResourceID(buttonView.getId());
                        }


                    break;
                case R.id.rb_custom_F:
                    if(isChecked){
                        Course.setGrade(-1);
                        Course.setResourceID(buttonView.getId());
                    }
                    break;



            }
        }
    }
    public int getColor(){
        if(toggle==0){
            toggle=1;
            return color[0];
        }else {
            toggle=0;
            return color[1];
        }
    }


}
