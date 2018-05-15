package com.afomic.spark.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by afomic on 18-Oct-16.
 */
public class TimeTableContract {
    public static final String TABLE_NAME = "timetable";
    public static final String COLUMN_VENUE = "venue";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LECTURER = "lecturer";

    public static void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ( ";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += COLUMN_NAME + " VARCHAR(10), ";
        sql += COLUMN_VENUE + " VARCHAR(10), ";
        sql += COLUMN_LECTURER + " VARCHAR(100), ";
        sql += COLUMN_TIME + " INTEGER(2), ";
        sql += COLUMN_DATE + " INTEGER(1), ";
        sql += COLUMN_COLOR + " INTEGER(10) )";
        db.execSQL(sql);

    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE " + TABLE_NAME;
        db.execSQL(dropTable);
    }
}
