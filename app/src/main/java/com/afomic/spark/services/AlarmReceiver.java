package com.afomic.spark.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.afomic.spark.R;
import com.afomic.spark.activities.MainActivity;
import com.afomic.spark.data.Constants;
import com.afomic.spark.model.TimeTableClass;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle args = intent.getExtras();
        NotificationService alarm = new NotificationService(context);
        String courseCode = args.getString(Constants.NAME);
        String courseVenue = args.getString(Constants.VENUE);
        int color = args.getInt(Constants.COLOR, Color.BLUE);
        int time = args.getInt(Constants.TIME);
        int date = args.getInt(Constants.DATE);
        TimeTableClass item = new TimeTableClass(time, courseCode, courseVenue, color, date, "");
        //set a new alarm when the old one is triggered
        alarm.scheduleNext(item);
        Intent sentIntent = new Intent(context, MainActivity.class);
        sentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 232, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(String.format("%s at %s", courseCode, courseVenue));
        builder.setContentText("In 10 minutes!!!");
        builder.setAutoCancel(true);
        builder.setColor(color);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setShowWhen(true);
        builder.setCategory(NotificationCompat.CATEGORY_SYSTEM);
        builder.setSmallIcon(R.drawable.notification_icon);
        //make the device vibrate
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        //make sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        if (Build.VERSION.SDK_INT >= 21) {
            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        }
        builder.setContentIntent(pi);
        builder.setWhen(System.currentTimeMillis());
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(3432, builder.build());
    }
}
