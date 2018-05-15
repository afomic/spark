package com.afomic.spark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.afomic.spark.R;
import com.afomic.spark.data.CourseData;
import com.afomic.spark.model.Course;

import java.util.ArrayList;

/**
 * Created by afomic on 03-Dec-16.
 * this is the course list adapter
 */
public class CourseListAdapter extends BaseExpandableListAdapter {
    Context context;
    private int level;
     private  ArrayList<Course> firstSemester,secondSemester;
     private CourseData dbData;
    int[] layouts={R.layout.restricted_econs, R.layout.restricted_eng,R.layout.restricted_math};
    private String[] semesters={"Harmattan Semester","Rain semester"};
    private String[] electives={"Special Electives","Restricted Electives"};
    public CourseListAdapter(Context c,int level){
        this.level=level;
        context=c;
        dbData=new CourseData(context);
        if(level!=0){//check to see if its not an Electives
            firstSemester=dbData.getCourseList(level, 1);
            secondSemester=dbData.getCourseList(level, 2);
        }

    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(level==0){//if the user choose to view elective return only one page
            return 1;
        }
        int count =0;
        if(groupPosition==0){
            count=firstSemester.size();
        }else {
            count=secondSemester.size();
        }
        return count+2;
    }

    @Override
    public String getGroup(int groupPosition) {
        if(level==0){
            return electives[groupPosition];
        }
        return semesters[groupPosition];
    }

    @Override
    public Course getChild(int groupPosition, int childPosition) {
        int count=childPosition-1;
        if(groupPosition==0){
            return firstSemester.get(count);
        }else {
            return secondSemester.get(count);
        }

    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.semester_course_item,parent,false);
        TextView semesterText=(TextView) convertView.findViewById(R.id.section_title);
        TextView semesterIndicator=(TextView) convertView.findViewById(R.id.section_number);
        String groupName=getGroup(groupPosition);
        String ind=String.valueOf(groupName.charAt(0));
        semesterText.setText(groupName);
        semesterIndicator.setText(ind);
        ImageView upDownImage=(ImageView) convertView.findViewById(R.id.down_up);
        if(isExpanded){
            upDownImage.setImageResource(R.drawable.ic_up);
        }else {
            upDownImage.setImageResource(R.drawable.ic_down);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Holder holder;
        if(level==0&&groupPosition==0){
            return LayoutInflater.from(context).inflate(R.layout.special_electives,parent,false);
        }else if(level==0&&groupPosition==1){
            return LayoutInflater.from(context).inflate(layouts[1],parent,false);
        }
        if(convertView==null){
            holder=new Holder();
            convertView=LayoutInflater.from(context).inflate(R.layout.course,parent,false);
            holder.CourseCode=(TextView)convertView.findViewById(R.id.detail_list_code);
            holder.CourseUnit=(TextView)convertView.findViewById(R.id.detail_list_unit);
            holder.CoursePrerequisite=(TextView)convertView.findViewById(R.id.detail_list_preq);
            holder.CourseTitle=(TextView)convertView.findViewById(R.id.detail_list_title);
            holder.background=convertView.findViewById(R.id.black_background);
            convertView.setTag(holder);
        }else {
            holder=(Holder) convertView.getTag();
        }
        if(childPosition==0){
            holder.CourseCode.setText("Course Code");
            holder.CourseTitle.setText("Course Title");
            holder.CoursePrerequisite.setText("prerequisite");
            holder.CourseUnit.setText("Unit");
        }else if(isLastChild){
            holder.CourseCode.setText("Total Unit");
            holder.CourseTitle.setText("");
            holder.CoursePrerequisite.setText("");
            holder.CourseUnit.setText(String.valueOf(getTotalUnit(groupPosition)));
            holder.background.setVisibility(View.VISIBLE);
        }else{
            Course item=getChild(groupPosition,childPosition);
            holder.CourseCode.setText(item.getCourseName());
            holder.CourseTitle.setText(item.getTitle());
            holder.CoursePrerequisite.setText(item.getPrerequisite());
            int unit=item.getCourseUnit();
            holder.CourseUnit.setText(String.valueOf(unit));
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //return the total number of unit of courses to be offered in the semester
    public int getTotalUnit(int semester){
        if(semester==0){
            return addUp(firstSemester);
        }else {
            return addUp(secondSemester);
        }
    }
    public class Holder{
        TextView CourseCode,CourseTitle,CourseUnit, CoursePrerequisite;
        View background;
    }
    public int addUp(ArrayList<Course> mCourses){
        int total=0;
        for (Course item: mCourses){
            total=total+item.getCourseUnit();
        }
        return total;
    }

}
