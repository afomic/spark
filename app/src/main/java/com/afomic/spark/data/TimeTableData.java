package com.afomic.spark.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.afomic.spark.model.TimeTableClass;

import java.util.ArrayList;

/**
 * Created by afomic on 18-Oct-16.
 *
 */
public class TimeTableData {
    SQLiteDatabase db;
    DbHelper helper;
    static ArrayList<TimeTableClass> entries=null;
    public TimeTableData(Context context){
        helper=new DbHelper(context);
    }


    public ContentValues getContentValue(TimeTableClass entry){
        ContentValues values=new ContentValues();
        values.put(TimeTableContract.COLUMN_VENUE,entry.getVenue());
        values.put(TimeTableContract.COLUMN_NAME,entry.getName());
        values.put(TimeTableContract.COLUMN_COLOR,entry.getColor());
        values.put(TimeTableContract.COLUMN_TIME,entry.getTime());
        values.put(TimeTableContract.COLUMN_DATE,entry.getDate());
        values.put(TimeTableContract.COLUMN_LECTURER,entry.getLecturer());
        return values;
    }

    public void addClass(TimeTableClass entry){
        try {
            db=helper.getWritableDatabase();
            db.insert(TimeTableContract.TABLE_NAME,null,getContentValue(entry));
            entries.add(entry);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db!=null){

                db.close();
            }
        }

    }



    public ArrayList<TimeTableClass> getTimeTable(int position){
        Cursor cu;
        try {
            db = helper.getReadableDatabase();
           // String[] selectionAgs={""+position};
            String selection=TimeTableContract.COLUMN_TIME + " = ?";
            String[] whereArg={""+position};
            cu =db.query(TimeTableContract.TABLE_NAME,null,selection,whereArg,null,null,null);
            ArrayList<TimeTableClass> timeTable = new ArrayList<>();
            while (cu.moveToNext()) {
                int time=cu.getInt(cu.getColumnIndex(TimeTableContract.COLUMN_TIME));
                int color =cu.getInt(cu.getColumnIndex(TimeTableContract.COLUMN_COLOR));
                int date =cu.getInt(cu.getColumnIndex(TimeTableContract.COLUMN_DATE));
                String venue=cu.getString(cu.getColumnIndex(TimeTableContract.COLUMN_VENUE));
                String name=cu.getString(cu.getColumnIndex(TimeTableContract.COLUMN_NAME));
                String lecturer=cu.getString(cu.getColumnIndex(TimeTableContract.COLUMN_LECTURER));
                TimeTableClass entry = new TimeTableClass(time,name,venue,color,date,lecturer);
                timeTable.add(entry);
            }
            return timeTable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (db != null) db.close();
        }
    }   public ArrayList<TimeTableClass> getTimeTable(){
        Cursor cu;
        try {
            db = helper.getReadableDatabase();
            // String[] selectionAgs={""+position};
            cu =db.query(TimeTableContract.TABLE_NAME,null,null,null,null,null,null);
            ArrayList<TimeTableClass> timeTable = new ArrayList<>();
            while (cu.moveToNext()) {
                int time=cu.getInt(cu.getColumnIndex(TimeTableContract.COLUMN_TIME));
                int color =cu.getInt(cu.getColumnIndex(TimeTableContract.COLUMN_COLOR));
                int date =cu.getInt(cu.getColumnIndex(TimeTableContract.COLUMN_DATE));
                String venue=cu.getString(cu.getColumnIndex(TimeTableContract.COLUMN_VENUE));
                String name=cu.getString(cu.getColumnIndex(TimeTableContract.COLUMN_NAME));
                String lecturer=cu.getString(cu.getColumnIndex(TimeTableContract.COLUMN_LECTURER));
                TimeTableClass entry = new TimeTableClass(time,name,venue,color,date,lecturer);
                timeTable.add(entry);
            }
            return timeTable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (db != null) db.close();
        }
    }
    public void updateClass(int date,int time,TimeTableClass update){
        db=helper.getWritableDatabase();
        ContentValues values=getContentValue(update);
        String where=TimeTableContract.COLUMN_TIME+"= ? AND "+TimeTableContract.COLUMN_DATE+" =?";
        String[] whereAgs={""+time,""+date};
        db.update(TimeTableContract.TABLE_NAME,values,where,whereAgs);

    }
    public void deleteClass(int date,int time){
        db=helper.getWritableDatabase();
        String where=TimeTableContract.COLUMN_TIME+"=? AND "+TimeTableContract.COLUMN_DATE+" =?";
        String[] whereAgs={""+time,""+date};
        db.delete(TimeTableContract.TABLE_NAME,where,whereAgs);
    }
    public TimeTableClass getClass(int time,int date){
        ArrayList<TimeTableClass> data=getTimeTable(time);
        for(TimeTableClass schedule: data){
            if(schedule.getDate()==date){
                return schedule;
            }
        }
        return null;
    }
}
