package com.afomic.spark.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by afomic on 18-Oct-16.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="nacoss";
    private static final int VERSION=1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CourseContract.onCreate(db);
        TimeTableContract.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        CourseContract.onUpgrade(db,oldVersion,newVersion);
        TimeTableContract.onUpgrade(db, oldVersion, newVersion);
    }
}
