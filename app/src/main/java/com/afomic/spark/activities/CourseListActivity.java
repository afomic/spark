package com.afomic.spark.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.fragment.CourseListDetailFragment;


/**
 * Created by afomic on 22-Oct-16.
 */
public class CourseListActivity extends AppCompatActivity {
    ViewPager pager;
    Toolbar toolbar;
    int level,option;
    TabLayout tabs;
    String[] title={"Electives","Part One","Part Two","Part Three","Part Four","Part Five"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_activity_layout);
        FragmentManager fm=getSupportFragmentManager();
        toolbar=(Toolbar)findViewById(R.id.pager_toolbar);
        tabs=(TabLayout) findViewById(R.id.course_tab_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pager=(ViewPager)findViewById(R.id.single_pager);
        tabs.setupWithViewPager(pager);

        //get the data sent from the course list Fragment
        Bundle passedData=getIntent().getExtras();
        level=passedData.getInt(Constants.LEVEL);
        option=passedData.getInt(Constants.OPTION);
        setTitle("Course List");

        pager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return CourseListDetailFragment.getInstance(position);
            }

            @Override
            public int getCount() {
                return 6;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        });

        pager.setCurrentItem(level);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

}
