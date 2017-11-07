package com.afomic.spark.util;

import android.content.Context;
import android.os.Environment;


import com.afomic.spark.data.TimeTableData;
import com.afomic.spark.model.TimeTableClass;
import com.afomic.spark.services.NotificationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by afomic on 14-Dec-16.
 * this is the class that convert the time table content into json
 */

public class TimeTableConvert {
    private TimeTableData dbData;
    private NotificationService alarm;
    public TimeTableConvert(Context c){
        dbData=new TimeTableData(c);
        alarm=new NotificationService(c);
    }
    public JSONArray toJsonArray() throws JSONException{
        ArrayList<TimeTableClass> classes=dbData.getTimeTable();
        JSONArray array=new JSONArray();
        for(TimeTableClass item:classes){
            array.put(item.toJson());
        }
        return array;
    }
    public void importTimeTable(JSONArray array)throws JSONException{
        for(int i=0;i<array.length();i++) {
            JSONObject object = array.getJSONObject(i);
            TimeTableClass item = new TimeTableClass(object);
            dbData.addClass(item);
            alarm.setAlarm(item);
        }
    }

    public void  saveData(String data){
        File f = new File(Environment.getExternalStorageDirectory(), "nacoss");
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(f,"TimeTable.txt");
        FileOutputStream os=null;
        try{
            os=new FileOutputStream(file);
            os.write(data.getBytes());
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
