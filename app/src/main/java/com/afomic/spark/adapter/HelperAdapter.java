package com.afomic.spark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.afomic.spark.R;


/**
 *
 * Created by afomic on 09-Dec-16.
 */

public class HelperAdapter extends BaseExpandableListAdapter {
    String[] questions={"how do i calculate my GPA with Gypee?","How do i add course to selection pane?","How can i persist data on Gypee","What does R.E means?",
    "How do i know the details of a course ?","i cant find the course am searching for","Am i to do only the listed Electives?"};
    String[] ans={"Gypee allows you to calculate your CGPA without stress,you can calculate your CGPA and GPA using the following steps \n" +
            "(1.) Select Gypee from the Nav bar.\n" +
            "(2.) Select the semester and level you will like to start calculating from,then select the departmental option\n " +
            "(3.) Click the  mark icon to move to move to the calculating page\n" +
            "(4.) Select each of the course you registered for, and select the grade for each of the courses \n" +
            "(5.) Click the mark icon to go to the result page\n" +
            "(6.) Click the next icon to view the next semester courses",
            "(1.) Click on the search icon\n"+
                    "(2.) Enter the Course code of the course you will like to add\n" +
                    "(3.) Select the course from the List\n",
            "(1.) On the result page select the save icon.\n"
            +"(2.) Set a unique password to save your data and progress\n"
            +"(3.) On the Gypee home select the restore icon\n"
            +"(4.) Enter your password to continue from where you left off\n",
            "R.E means Restricted Electives, this is a type of electives which adds to your CGPA",
            "(1.) Goto Gypee and enter any semester, level and option.\n" +
                    "(2.) Search for the course name.\n " +
                    "(3.) Long press the course you will like to see its details.",
            "please note, course are written with space in between the alphabet part and the numeric part.\n" +
                    "So CHM102 is CHM 102",
            "NO,those are just the list of the compulsory electives you must do before you graduate"
    };
    Context context;
    public HelperAdapter(Context c){
        context=c;
    }
    @Override
    public int getGroupCount() {
        return questions.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        QuestionHolder holder;
        if(view==null){
            holder= new QuestionHolder();
            view=LayoutInflater.from(context).inflate(R.layout.help_question_view,viewGroup,false);
            holder.question=(TextView) view.findViewById(R.id.question_text);
            holder.questionNum=(TextView)view.findViewById(R.id.question_no);
            view.setTag(holder);
        }else {
            holder=(QuestionHolder)view.getTag();
        }
        int num=i+1;
        holder.question.setText(questions[i]);
        holder.questionNum.setText(String.valueOf(num));
        return view;
    }
    private class QuestionHolder{
        TextView  question,questionNum;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        TextView answerText=new TextView(context);
        answerText.setText(ans[i]);
        answerText.setPadding(16,16,16,16);
        return answerText;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
