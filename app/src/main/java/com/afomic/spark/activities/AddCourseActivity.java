package com.afomic.spark.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.CourseData;
import com.afomic.spark.model.Course;

import java.util.ArrayList;

/**
 * Created by afomic on 16-Jan-17.
 */

public class AddCourseActivity extends AppCompatActivity {
    RadioButton hundred,twoHundred,threeHundred,fourHundred,fiveHundred,firstSemester,secondSemester;
    int level, semester,  option,unit;
    Button btnAdd,btnDone;
    EditText edtCourseCode,edtCourseTitle,edtCourseUnit;
    String courseCode,courseTitle;
    CourseData dbData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_couse_layout);
        Toolbar toolbar=(Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ADD COURSE");
        option=getIntent().getIntExtra(Constants.OPTION,0);
        dbData=new CourseData(this);
        edtCourseCode=(EditText) findViewById(R.id.edt_course_code);
        edtCourseTitle=(EditText) findViewById(R.id.edt_course_title);
        edtCourseUnit=(EditText) findViewById(R.id.edt_course_unit);


        btnAdd=(Button) findViewById(R.id.btn_add);
        btnDone=(Button) findViewById(R.id.btn_done);

        hundred=(RadioButton) findViewById(R.id.rb_level_hundred);
        twoHundred=(RadioButton) findViewById(R.id.rb_level_two_hundred);
        threeHundred=(RadioButton) findViewById(R.id.rb_level_three_hundred);
        fourHundred=(RadioButton) findViewById(R.id.rb_level_four_hundred);
        fiveHundred=(RadioButton) findViewById(R.id.rb_level_five_hundred);
        firstSemester=(RadioButton) findViewById(R.id.rb_semester_first);
        secondSemester=(RadioButton) findViewById(R.id.rb_semester_second);

        //setting action listener to each of the widget
        hundred.setOnCheckedChangeListener(new RbListner());
        threeHundred.setOnCheckedChangeListener(new RbListner());
        twoHundred.setOnCheckedChangeListener(new RbListner());
        fourHundred.setOnCheckedChangeListener(new RbListner());
        fiveHundred.setOnCheckedChangeListener(new RbListner());
        firstSemester.setOnCheckedChangeListener(new RbListner());
        secondSemester.setOnCheckedChangeListener(new RbListner());

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){
                    unit= Integer.parseInt(getValue(edtCourseUnit));
                    courseCode=getValue(edtCourseCode);
                    courseTitle=getValue(edtCourseTitle).toUpperCase();
                    Course item=new Course(courseCode,option,unit,level,semester,"",courseTitle);
                    ArrayList<Course> courses=new ArrayList<>();
                    courses.add(item);
                    dbData.addCourse(courses);
                    Toast.makeText(AddCourseActivity.this,courseTitle+" Successfully added",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean checkFields(){
       if(getValue(edtCourseCode).equals("")){
           Toast.makeText(this,"Course Code cannot be empty",Toast.LENGTH_SHORT).show();
           return false;
       }
        if(getValue(edtCourseUnit).equals("")){
            Toast.makeText(this,"Course Unit cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(semester==0){
            Toast.makeText(this,"Please Select a semester",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (level==0){
            Toast.makeText(this,"Please Select a level",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private String getValue(EditText edt){
        return edt.getText().toString();
    }


    public class RbListner implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.rb_level_hundred:
                    if(isChecked){
                        level=1;
                    }
                    break;
                case R.id.rb_level_two_hundred:
                    if(isChecked){
                        level=2;
                    }
                    break;
                case R.id.rb_level_three_hundred:
                    if(isChecked){
                        level=3;
                    }
                    break;
                case R.id.rb_level_four_hundred:
                    if(isChecked){
                        level=4;
                    }
                    break;
                case R.id.rb_level_five_hundred:
                    if(isChecked){
                        level=5;
                    }
                    break;
                case R.id.rb_semester_first:
                    if(isChecked){
                        semester=1;
                    }
                    break;
                case R.id.rb_semester_second:
                    if(isChecked){
                        semester=2;
                    }
                    break;

            }
        }
    }
}
