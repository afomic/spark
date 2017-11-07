package com.afomic.spark.model;

import android.graphics.Color;


import com.afomic.spark.data.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afomic on 17-Oct-16.
 */
public class TimeTableClass {
    String name,venue,lecturer;
    int color,time,date;

    public TimeTableClass(int time, String name, String venue, int color,int date,String lecturer) {
        this.time = time;
        this.date=date;
        this.name = name;
        this.color=color;
        this.lecturer=lecturer;
        this.venue=venue;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public String getLecturer() {
        return lecturer;
    }

    public int getColor() {
        if(color==0){
            return Color.argb(180,6,156,134);
        }
        return color;
    }

    public int getDate() {
        return date;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public JSONObject toJson() throws JSONException{
        JSONObject object=new JSONObject();
        object.put(Constants.NAME,getName());
        object.put(Constants.VENUE,getVenue());
        object.put(Constants.DATE,getDate());
        object.put(Constants.TIME,getTime());
        object.put(Constants.LECTURER,getLecturer());
        object.put(Constants.COLOR,getColor());
        return object;
    }
    public TimeTableClass (JSONObject object) throws JSONException{
        venue=object.getString(Constants.VENUE);
        date=object.getInt(Constants.DATE);
        time=object.getInt(Constants.TIME);
        color=object.getInt(Constants.COLOR);
        name=object.getString(Constants.NAME);
        lecturer=object.getString(Constants.LECTURER);
    }


}
