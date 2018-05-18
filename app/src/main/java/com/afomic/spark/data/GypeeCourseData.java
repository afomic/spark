package com.afomic.spark.data;

import android.content.Context;

import com.afomic.spark.model.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by afomic on 18-Oct-16.
 */
public class GypeeCourseData {
    static ArrayList<Course> courses = new ArrayList<>();

    public static void addCourse(String departmentName) {
        FirebaseDatabase.getInstance().getReference("course")
                .child(departmentName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Course course = snapshot.getValue(Course.class);
                            courses.add(course);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public static void addData(Context context) {
        PreferenceManager preferenceManager=new PreferenceManager(context);
        CourseData db = new CourseData(context);
        if (db.isEmpty()) {
            String courseChildNode=preferenceManager.getOption()==null?preferenceManager.getAssociationName():preferenceManager.getOption();
            addCourse(courseChildNode);
            db.addCourse(courses);
        }

    }


}
