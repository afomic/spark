package com.afomic.spark.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.afomic.spark.model.Course;

import java.util.ArrayList;

/**
 * Created by afomic on 18-Oct-16.
 */
public class CourseData {
    SQLiteDatabase db;
    DbHelper helper;

    public CourseData(Context context) {
        helper = new DbHelper(context);
    }

    //insert a course into the database
    public void addCourse(Course entry, SQLiteDatabase db) {
        db.insert(CourseContract.TABLE_NAME, null, getContentValue(entry));
    }

    //return an array list of courses
    public ArrayList<Course> getCourse(int level, int semester) {
        Cursor cu;
        try {
            db = helper.getReadableDatabase();
            String[] projection = {CourseContract.COURSE_NAME, CourseContract.COURSE_UNIT};
            String[] whereArgs = {"" + level, "" + semester};//get course which semester,level and option equals the required course
            cu = db.query(CourseContract.TABLE_NAME, projection,
                    CourseContract.COURSE_LEVEL + " =? AND " + CourseContract.COURSE_SEMESTER + " =?",
                    whereArgs, null, null, null);
            ArrayList<Course> entries = new ArrayList<>();
            while (cu.moveToNext()) {
                Course entry = new Course();
                entry.setCourseName(cu.getString(cu.getColumnIndex(CourseContract.COURSE_NAME)));
                entry.setCourseUnit(cu.getInt(cu.getColumnIndex(CourseContract.COURSE_UNIT)));
                entries.add(entry);
            }
            cu.close();
            return entries;
        } catch (Exception e) {
            return null;
        } finally {
            if (db != null) db.close();
        }
    }

    public ArrayList<Course> getCourseList(int level, int semester, int option) {
        Cursor cu;
        try {
            db = helper.getReadableDatabase();
            String[] projection = {CourseContract.COURSE_NAME, CourseContract.COURSE_UNIT, CourseContract.COURSE_PREQ, CourseContract.COURSE_TITLE};
            String[] whereArgs = {"" + level, "" + semester, "" + option};//get course which semester,level and option equals the required course
            cu = db.query(CourseContract.TABLE_NAME, projection,
                    CourseContract.COURSE_LEVEL + " =? AND " + CourseContract.COURSE_SEMESTER + " =?" ,
                    whereArgs, null, null, null);
            ArrayList<Course> entries = new ArrayList<>();
            while (cu.moveToNext()) {
                int unit = cu.getInt(cu.getColumnIndexOrThrow(CourseContract.COURSE_UNIT));
                String name = cu.getString(cu.getColumnIndexOrThrow(CourseContract.COURSE_NAME));
                String title = cu.getString(cu.getColumnIndexOrThrow(CourseContract.COURSE_TITLE));
                String preq = cu.getString(cu.getColumnIndexOrThrow(CourseContract.COURSE_PREQ));
                Course tempCourse = new Course(name, unit, level, semester, preq, title);
                entries.add(tempCourse);

            }
            cu.close();
            return entries;
        } catch (Exception e) {
            return null;
        } finally {
            if (db != null) db.close();
        }
    }

    public void addCourse(ArrayList<Course> entries) {
        db = helper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (Course entry : entries) {
                addCourse(entry, db);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    public void updateCourse(String category, String name) {

    }

    public void deleteCourse(String category, String name) {

    }

    public ContentValues getContentValue(Course entry) {
        ContentValues values = new ContentValues();
        values.put(CourseContract.COURSE_LEVEL, entry.getCourseLevel());
        values.put(CourseContract.COURSE_NAME, entry.getCourseName());
        values.put(CourseContract.COURSE_SEMESTER, entry.getCourseSemester());
        values.put(CourseContract.COURSE_UNIT, entry.getCourseUnit());
        values.put(CourseContract.COURSE_PREQ, entry.getPrerequisite());
        values.put(CourseContract.COURSE_TITLE, entry.getTitle());
        return values;
    }


    public boolean isEmpty() {
        try {
            db = helper.getReadableDatabase();
            Cursor cursor = db.query(CourseContract.TABLE_NAME, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public ArrayList<Course> search(String query) {

        String[] selection = {"%" + query + "%"};
        return getSearch(selection);

    }

    private ArrayList<Course> getSearch(String[] selection) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<Course> searchResult = new ArrayList<>();
        try {
            db = helper.getReadableDatabase();
            cursor = db.query(CourseContract.TABLE_NAME, null, CourseContract.COURSE_NAME + " LIKE ?", selection, null, null, null);
            while (cursor.moveToNext()) {
                int unit = cursor.getInt(cursor.getColumnIndexOrThrow(CourseContract.COURSE_UNIT));
                int level = cursor.getInt(cursor.getColumnIndexOrThrow(CourseContract.COURSE_LEVEL));
                int semester = cursor.getInt(cursor.getColumnIndexOrThrow(CourseContract.COURSE_SEMESTER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CourseContract.COURSE_NAME));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(CourseContract.COURSE_TITLE));
                String preq = cursor.getString(cursor.getColumnIndexOrThrow(CourseContract.COURSE_PREQ));
                Course tempCourse = new Course(name, unit, level, semester, preq, title);
                searchResult.add(tempCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return searchResult;
    }

}
