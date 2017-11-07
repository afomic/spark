package com.afomic.spark.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import com.afomic.spark.data.Constants;
import com.afomic.spark.data.TimeTableData;
import com.afomic.spark.model.TimeTableClass;

import java.util.Calendar;


/**
 * Created by afomic on 30-Nov-16.
 * this add an alarm to the alarm manager using the time of the class and the venue
 * it will make the alarm manager start the notification that will notify the user of a class
 *
 */
public class NotificationService {
    private Context context;
    private AlarmManager am;
    private int[] dates={Calendar.MONDAY,Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY,Calendar.FRIDAY};
    public NotificationService(Context c){
        context=c;
        am=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    public void setAlarm(TimeTableClass item){
        Calendar alarmTime=getCalendar(item);
        TimeTableClass previousClass=getPreviousSchedule(item);
        if(previousClass!=null&&item.getName().equals(previousClass.getName())) return;
        int uniqueId=getId(item);
        long time=alarmTime.getTimeInMillis();
        long currentTime=System.currentTimeMillis();
        if(currentTime>time){
            scheduleNext(item);
        }else{
            setAlarm(time,uniqueId,item);
        }


    }
    public void cancelAlarm(TimeTableClass item){
        int time=item.getTime()+6;//the time start at 0,but the timetable start at 7am
        int date=dates[item.getDate()];
        int uniqueId=(7*time) +(13*date);
        Intent intent=new Intent(context,AlarmReceiver.class);
        intent.putExtra(Constants.NAME,item.getName());
        intent.putExtra(Constants.VENUE,item.getVenue());
        intent.putExtra(Constants.COLOR,item.getColor());
        PendingIntent pi=PendingIntent.getBroadcast(context,uniqueId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
    }
    public void scheduleNext(TimeTableClass item){
        Calendar alarmTime=getCalendar(item);
        int id=getId(item);
        long newTime=alarmTime.getTimeInMillis()+(1000*24*7*60*60);//add the the  seven  days interval to the next alarm time
        setAlarm(newTime,id,item);
    }
    private Calendar getCalendar(TimeTableClass item){
        int time=item.getTime()+6;//the time start at 0,but the timetable start at 7am
        int date=dates[item.getDate()];//the date start at 0,but the date start a 1 which connotes monday
        Calendar alarmTime=Calendar.getInstance();
        alarmTime.setTimeInMillis(System.currentTimeMillis());
        alarmTime.set(Calendar.DAY_OF_WEEK,date);
        alarmTime.set(Calendar.HOUR_OF_DAY,time);
        alarmTime.set(Calendar.MINUTE,0);
        alarmTime.set(Calendar.SECOND,0);
        alarmTime.set(Calendar.MILLISECOND,0);
        return alarmTime;
    }
    private int getId(TimeTableClass item){
        int time=item.getTime()+6;//the time start at 0,but the timetable start at 7am
        int date=dates[item.getDate()];
        return (7*time) +(13*date);
    }
    private void  setAlarm(long time,int uniqueId,TimeTableClass item){
        Intent intent=new Intent(context,AlarmReceiver.class);
        intent.putExtra(Constants.TIME,item.getTime());
        intent.putExtra(Constants.DATE,item.getDate());
        intent.putExtra(Constants.NAME,item.getName());
        intent.putExtra(Constants.VENUE,item.getVenue());
        intent.putExtra(Constants.COLOR,item.getColor());
        PendingIntent pi=PendingIntent.getBroadcast(context,uniqueId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        int ALARM_TYPE = AlarmManager.RTC_WAKEUP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            am.setExactAndAllowWhileIdle(ALARM_TYPE,time, pi);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            am.setExact(ALARM_TYPE,time, pi);
        }
        else{
            am.set(ALARM_TYPE, time, pi);
        }

    }
    public TimeTableClass getPreviousSchedule(TimeTableClass item){
        int time =item.getTime()-1;
        int date=item.getDate();
        TimeTableData dbData=new TimeTableData(context);
        return dbData.getClass(time,date);
    }





}