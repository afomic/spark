package com.afomic.spark.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afomic.spark.R;
import com.afomic.spark.adapter.ColorPickerAdapter;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.TimeTableData;
import com.afomic.spark.model.TimeTableClass;
import com.afomic.spark.services.NotificationService;


/**
 * Created by afomic on 23-Oct-16.
 */
public class SetClassDialog extends DialogFragment {
    int time, date, color;
    String venue, name, action;
    EditText courseNameET, courseVenueET, courseLecturerET;
    CourseListener callback;
    Spinner backgroundColorPicker;
    TimeTableData dbData;
    NotificationService alarm;
    int[] colors = {0, Color.rgb(244, 67, 54), Color.rgb(121, 85, 72), Color.rgb(156, 39, 176), Color.rgb(96, 125, 139),
            Color.rgb(0, 188, 212), Color.rgb(255, 193, 7), Color.rgb(254, 121, 48), Color.rgb(254, 48, 101), Color.rgb(0, 77, 64),
            Color.rgb(83, 83, 83), Color.rgb(34, 120, 161)};

    public interface CourseListener {
        public void classAdded();
    }


    public static SetClassDialog getInstance(int time, int date, String action) {
        SetClassDialog dialog = new SetClassDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TIME, time);
        bundle.putString(Constants.ACTION, action);
        bundle.putInt(Constants.DATE, date);
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        time = bundle.getInt(Constants.TIME);
        date = bundle.getInt(Constants.DATE);
        action = bundle.getString(Constants.ACTION);
        callback = (CourseListener) getTargetFragment();
        alarm = new NotificationService(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.add_class_layout, null);
        courseNameET = (EditText) v.findViewById(R.id.et_course_name);
        courseVenueET = (EditText) v.findViewById(R.id.et_course_venue);
        courseLecturerET = (EditText) v.findViewById(R.id.et_course_lecturer);
        TextView courseTime = (TextView) v.findViewById(R.id.course_time);
        TextView addText = (TextView) v.findViewById(R.id.time_class_add);
        TextView cancelText = (TextView) v.findViewById(R.id.time_class_cancel);
        backgroundColorPicker = (Spinner) v.findViewById(R.id.color_picker);
        backgroundColorPicker.setAdapter(new ColorPickerAdapter(getActivity()));
        backgroundColorPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color = colors[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dbData = new TimeTableData(getContext());
        courseTime.setText(getTime(time, date));
        builder.setView(v);
        if (action.equals("add")) {
            addText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = courseNameET.getText().toString();
                    if (name.equals("")) {
                        Toast.makeText(getActivity(), "Course name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        venue = courseVenueET.getText().toString();
                        TimeTableClass item = new TimeTableClass(time, name.toUpperCase(), venue, color, date, courseLecturerET.getText().toString());
                        dbData.addClass(item);
                        callback.classAdded();
                        alarm.setAlarm(item);
                        dismiss();
                    }

                }
            });
        } else {
            TimeTableClass item = dbData.getClass(time, date);
            courseLecturerET.setText(item.getLecturer());
            courseNameET.setText(item.getName());
            courseVenueET.setText(item.getVenue());
            addText.setText("UPDATE");
            addText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    name = courseNameET.getText().toString();
                    if (name.equals("")) {
                        Toast.makeText(getActivity(), "Course name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        venue = courseVenueET.getText().toString();
                        TimeTableClass item = new TimeTableClass(time, name, venue, color, date, courseLecturerET.getText().toString());
                        dbData.updateClass(date, time, item);
                        callback.classAdded();
                        dismiss();
                    }

                }
            });
        }


        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }

    public String getTime(int time, int date) {
        String[] dates = {"MON", "TUE", "WED", "THUR", "FRI"};
        String ans;
        int startTime = time + 7;
        if (startTime < 12) {
            ans = String.format("%s, %d:00 - %d:00 am", dates[date], startTime, (startTime + 1));
        } else if (startTime == 12) {
            ans = dates[date] + ", 12:00 - 1:00 pm";
        } else {
            startTime = startTime % 12;
            ans = String.format("%s, %d:00 - %d:00 pm", dates[date], startTime, (startTime + 1));
        }
        return ans;
    }
}
