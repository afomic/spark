package com.afomic.spark.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.model.Course;


/**
 * Created by afomic on 07-Dec-16.
 */

public class CourseDetailsDialog extends DialogFragment {
    int semester, unit, level;
    String courseCode, courseTitle, coursePreq;
    String[] sem = {"", "Harmattan", "Rain"};

    public static CourseDetailsDialog newInstance(Course item) {
        CourseDetailsDialog dialog = new CourseDetailsDialog();
        Bundle args = new Bundle();
        args.putString(Constants.NAME, item.getCourseName());
        args.putInt(Constants.LEVEL, item.getCourseLevel());
        args.putString(Constants.TITLE, item.getTitle());
        args.putString(Constants.PREQ, item.getPrerequisite());
        args.putInt(Constants.SEMESTER, item.getCourseSemester());
        args.putInt(Constants.UNIT, item.getCourseUnit());
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        semester = bundle.getInt(Constants.SEMESTER);
        level = bundle.getInt(Constants.LEVEL);
        unit = bundle.getInt(Constants.UNIT);
        courseCode = bundle.getString(Constants.NAME);
        courseTitle = bundle.getString(Constants.TITLE);
        coursePreq = bundle.getString(Constants.PREQ);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.course_detail, null);
        builder.setView(v);
        TextView mName = (TextView) v.findViewById(R.id.course_detail_code);
        TextView mLevel = (TextView) v.findViewById(R.id.course_detail_level);
        TextView mSemester = (TextView) v.findViewById(R.id.course_detail_semester);
        TextView mType = (TextView) v.findViewById(R.id.course_detail_type);
        TextView mTitle = (TextView) v.findViewById(R.id.course_detail_title);
        TextView mPreq = (TextView) v.findViewById(R.id.course_detail_prerequisite);
        TextView mUnit = (TextView) v.findViewById(R.id.course_detail_unit);
        mName.setText(courseCode);
        mLevel.setText(String.valueOf(level));
        mSemester.setText(sem[semester]);
        mTitle.setText(courseTitle);
        mPreq.setText(coursePreq);
        mUnit.setText(String.valueOf(unit));
        if (courseTitle.contains("R.E")) {
            mType.setText("Restricted Electives");
        }

        return builder.create();
    }
}
