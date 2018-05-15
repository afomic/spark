package com.afomic.spark.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.TimeTableData;
import com.afomic.spark.model.TimeTableClass;
import com.afomic.spark.services.NotificationService;


/**
 * Created by afomic on 27-Nov-16.
 */
public class TimeTableDetailDialog extends DialogFragment {
    String venue, lecturer, name;
    int time, date, color;
    TimeTableData tableData;
    ChangeListener callback;
    NotificationService alarm;

    public interface ChangeListener {
        public void onModify(int time, int date);

        public void onDelete();
    }


    public static TimeTableDetailDialog getInstance(TimeTableClass schedule) {
        TimeTableDetailDialog fragment = new TimeTableDetailDialog();
        Bundle arg = new Bundle();
        arg.putInt(Constants.TIME, schedule.getTime());
        arg.putInt(Constants.COLOR, schedule.getColor());
        arg.putInt(Constants.DATE, schedule.getDate());
        arg.putString(Constants.NAME, schedule.getName());
        arg.putString(Constants.LECTURER, schedule.getLecturer());
        arg.putString(Constants.VENUE, schedule.getVenue());
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        name = bundle.getString(Constants.NAME);
        venue = bundle.getString(Constants.VENUE);
        lecturer = bundle.getString(Constants.LECTURER);
        time = bundle.getInt(Constants.TIME);
        date = bundle.getInt(Constants.DATE);
        color = bundle.getInt(Constants.COLOR);
        tableData = new TimeTableData(getActivity());
        callback = (ChangeListener) getTargetFragment();
        alarm = new NotificationService(getActivity());

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.time_table_detail, null);
        TextView className = (TextView) v.findViewById(R.id.tv_detail_name);
        TextView classVenue = (TextView) v.findViewById(R.id.tv_detail_venue);
        TextView classTime = (TextView) v.findViewById(R.id.tv_detail_time);
        TextView classLecturer = (TextView) v.findViewById(R.id.tv_detail_lecturer);
        //set the value for each of the element in the dialog
        classLecturer.setText(lecturer);
        className.setText(name);
        className.setBackgroundColor(color);
        classTime.setText(getTime(time, date));
        classVenue.setText(venue);
        builder.setView(v);
        final TextView delete = (TextView) v.findViewById(R.id.action_delete);
        TextView modify = (TextView) v.findViewById(R.id.action_modify);
        TextView ok = (TextView) v.findViewById(R.id.action_ok);
        delete.setTextColor(color);
        modify.setTextColor(color);
        ok.setTextColor(color);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableData.deleteClass(date, time);
                callback.onDelete();
                TimeTableClass item = new TimeTableClass(time, name, venue, color, date, lecturer);
                alarm.cancelAlarm(item);
                dismiss();
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onModify(time, date);
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
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
