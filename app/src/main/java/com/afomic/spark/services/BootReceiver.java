package com.afomic.spark.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.afomic.spark.data.TimeTableData;
import com.afomic.spark.model.TimeTableClass;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by afomic on 06-Dec-16.
 */

public class BootReceiver extends BroadcastReceiver {

    int[] dates = {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY};

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationService alarm = new NotificationService(context);
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            TimeTableData data = new TimeTableData(context);
            ArrayList<TimeTableClass> classes = data.getTimeTable();
            for (TimeTableClass event : classes) {
                alarm.setAlarm(event);
            }
        }
    }

}
