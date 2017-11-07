package com.afomic.spark.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;


import com.afomic.spark.R;
import com.afomic.spark.activities.AddCourseActivity;
import com.afomic.spark.adapter.GridAdapter;
import com.afomic.spark.adapter.SuggestionAdapter;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.CourseData;
import com.afomic.spark.model.Course;
import com.afomic.spark.model.Gypee;
import com.afomic.spark.util.NonScrollListView;

import java.util.ArrayList;


/**
 * Created by afomic on 22-Oct-16.
 *
 */
public class GypeeCalculateFragment extends Fragment {
    ArrayList<Course> userCourse;
    ArrayList<Course> selectedCourse;
    Gypee userGypee;
    int semester,level,option;
    double totalPoint,totalUnit;
    int tempTotalUnit;
    Bundle bundle;
    GridAdapter adapter;
    SuggestionAdapter searchAdapter;
    NonScrollListView gradingList;
    GridLayout layout;
    CourseData courseData;
    Callback mCallback;
    SearchView courseSearch;
    ListView searchList;
    LinearLayout searchLayout,calculateLayout;
    Button addNewCourse;
    ScrollView scrollContainer;
    MenuItem menuItem;
    public static GypeeCalculateFragment getInstance(int level,int semester,int option,double totalPoint,double totalUnit){
        GypeeCalculateFragment fragment=new GypeeCalculateFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.LEVEL,level);
        bundle.putInt(Constants.SEMESTER,semester);
        bundle.putInt(Constants.OPTION,option);
        bundle.putDouble(Constants.TOTAL_POINT, totalPoint);
        bundle.putDouble(Constants.TOTAL_UNIT, totalUnit);
        fragment.setArguments(bundle);
        return fragment;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback=(Callback)context;
    }

    public interface Callback{
        public void onDoneClick(int semester, int level, int option, double totalPoint, double totalUnit, double previousPoint, double previousUnit, String cgpa, String gpa);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg=getArguments();
        option=arg.getInt(Constants.OPTION,0);
        level=arg.getInt(Constants.LEVEL,1);
        semester=arg.getInt(Constants.SEMESTER,1);
        totalPoint=arg.getDouble(Constants.TOTAL_POINT, 0);
        totalUnit=arg.getDouble(Constants.TOTAL_UNIT, 0);
        Log.d(Constants.TAG, "calculae total point" + totalPoint + " total unit received" + totalUnit);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.gypee_calculate,container,false);
        gradingList=(NonScrollListView) v.findViewById(R.id.my_recycle);
        layout=(GridLayout)v.findViewById(R.id.my_grid);
        //initialize the layout and the views for the calculate fragment
        searchList=(ListView)v.findViewById(R.id.search_list);
        calculateLayout=(LinearLayout) v.findViewById(R.id.gypee_calculate_container);
        searchLayout=(LinearLayout) v.findViewById(R.id.search_container);
        addNewCourse=(Button) v.findViewById(R.id.search_add_button);
        scrollContainer=(ScrollView) v.findViewById(R.id.gypee_scrol);
        //adding adapter to the list view
        courseData=new CourseData(getContext());
        //  create an object of database
        userGypee=new Gypee(totalUnit,totalPoint);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = searchAdapter.getItem(position);
                addCheckBox(course, layout);
                //making the search layout Gone after a Course as being added
                searchLayout.setVisibility(View.GONE);
                courseSearch.onActionViewCollapsed();
                menuItem.collapseActionView();
                hideKeyboard();
            }
        });
        searchList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course item=searchAdapter.getItem(i);
                CourseDetailsDialog dialog=CourseDetailsDialog.newInstance(item);
                dialog.show(getFragmentManager(),"");
                return true;
            }
        });
        addNewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.GONE);
                courseSearch.onActionViewCollapsed();
                menuItem.collapseActionView();
                Intent intent=new Intent(getActivity(), AddCourseActivity.class);
                intent.putExtra(Constants.OPTION,option);
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);
        process();
        return v;

    }


    public void process(){
        userCourse=courseData.getCourse(level, semester, option);
        selectedCourse=new ArrayList<>();
        addCheckBox(userCourse, layout);

    }

    public void addCheckBox(Course course,GridLayout layout){
        CheckBox checkBox=new CheckBox(getContext());
        checkBox.setText(course.getCourseName());
        checkBox.setOnCheckedChangeListener(new checkBoxListener(course.getCourseUnit(),course.getCourseName()));
        layout.addView(checkBox);

    }


    public void addCheckBox(ArrayList<Course> courses,GridLayout gridLayout){
        for(Course course:courses){
            addCheckBox(course,gridLayout);
        }
    }

    public int  getTotalPoint(ArrayList<Course> array){
        int myTotalPoint=0;
        ArrayList<Course> carriedCourse=new ArrayList<>();
        for(Course course:array){
            if(course.getGrade()==-1){
                Course carry=new Course(course.getCourseName(),option,course.getCourseUnit(),level+1,semester,course.getPrerequisite(),course.getTitle());
                carriedCourse.add(carry);
            }else {
                myTotalPoint=myTotalPoint+(course.getGrade()*course.getCourseUnit());
            }

        }
        courseData.addCourse(carriedCourse);
        return myTotalPoint;
    }


    public class checkBoxListener implements CompoundButton.OnCheckedChangeListener{

        Course tempCourse;
        public checkBoxListener(int unit,String name) {
            tempCourse =new Course(name,unit);

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                selectedCourse.add(tempCourse);
                tempTotalUnit=tempTotalUnit+tempCourse.getCourseUnit();
            }
            else {
                selectedCourse.remove(tempCourse);
                tempTotalUnit=tempTotalUnit-tempCourse.getCourseUnit();
            }
            adapter= new GridAdapter(selectedCourse);
            gradingList.setAdapter(adapter);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    scrollContainer.smoothScrollTo(0,scrollContainer.getBottom());
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gypee_calculate, menu);
        menuItem =menu.findItem(R.id.menu_course_search);
        courseSearch=(SearchView) MenuItemCompat.getActionView(menuItem);
        courseSearch.setLayoutParams(new ActionBar.LayoutParams(Gravity.RIGHT));
        courseSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals("")){
                    ArrayList<Course> tempSearchArray=removeDoubleEntry(courseData.search(newText));
                    searchLayout.setVisibility(View.VISIBLE);
                    searchAdapter = new SuggestionAdapter(getActivity(),R.layout.course_search_item,tempSearchArray);
                    searchList.setAdapter(searchAdapter);
                }

                return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_mark:
                if(tempTotalUnit!=0){
                    FragmentManager fm=getFragmentManager();
                    double previousPoint=userGypee.getCtotalPoint();
                    double previousUnit=userGypee.getCtotalUnit();
                    int currentPoint=getTotalPoint(selectedCourse);
                    userGypee.setTotalUnit(tempTotalUnit);
                    userGypee.setTotalPoint(currentPoint);
                    userGypee.addToCgpa(currentPoint, tempTotalUnit);
                    totalUnit=userGypee.getCtotalUnit();
                    totalPoint=userGypee.getCtotalPoint();
                    String gpa=String.format("%.2f", userGypee.getGpa());
                    String cgpa=String.format("%.2f", userGypee.getCgpa());
                    mCallback.onDoneClick(semester,level,option,totalPoint,totalUnit,previousPoint,previousUnit,cgpa,gpa);
                }  else{
                    Toast.makeText(getContext(),"You must select at least one Courses",Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
               getActivity().finish();

        }
        return true;
    }
    public ArrayList<Course> removeDoubleEntry(ArrayList<Course> arrayList){
        ArrayList<Course> truncatedArray=new ArrayList<>();
        for(Course entry:arrayList){
            if(!isContained(truncatedArray,entry)){
                truncatedArray.add(entry);
            }
        }
        return truncatedArray;
    }
    public boolean isContained(ArrayList<Course> array,Course course){
        for(Course entry:array){
            if(entry.getCourseName().equals(course.getCourseName())){
                return true;
            }
        }
        return false;
    }
    public void hideKeyboard(){
        InputMethodManager imm=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(courseSearch.getWindowToken(), 0);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback=null;
    }

}
