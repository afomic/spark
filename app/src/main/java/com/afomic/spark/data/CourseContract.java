package com.afomic.spark.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by afomic on 18-Oct-16.
 */
public class CourseContract {
    public static final String TABLE_NAME="course";
    public static final String COURSE_NAME="course_name";
    public static final String COURSE_UNIT="course_unit";
    public static final String COURSE_OPTION="course_option";
    public static final String COURSE_LEVEL="course_level";
    public static final String COURSE_SEMESTER="course_semester";
    public static final String COURSE_TITLE="course_title";
    public static final String COURSE_PREQ="course_prerequisite";

    public static void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+TABLE_NAME+" ( ";
        sql+="_id INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql+=COURSE_NAME+" VARCHAR(7), ";
        sql+=COURSE_TITLE+" VARCHAR(100), ";
        sql+=COURSE_PREQ+" VARCHAR(7), ";
        sql+=COURSE_UNIT+" INTEGER(1), ";
        sql+=COURSE_OPTION+" INTEGER(1), ";
        sql+=COURSE_LEVEL+" INTEGER(1), ";
        sql+=COURSE_SEMESTER+" INTEGER(1) )";
        db.execSQL(sql);

    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable="DROP TABLE"+TABLE_NAME;
        db.execSQL(dropTable);
    }
}
