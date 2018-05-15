package com.afomic.spark.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.afomic.spark.R;
import com.afomic.spark.activities.CourseListActivity;
import com.afomic.spark.adapter.ExpendableListAdapter;
import com.afomic.spark.data.Constants;


/**
 * Created by afomic on 18-Oct-16.
 */
public class CourseListFragment extends Fragment {
    ViewPager pager;
    int level=0;
    TabLayout tabs;
    String[] title={"Electives","Part One","Part Two","Part Three","Part Four","Part Five"};
    public static CourseListFragment getInstance() {
        CourseListFragment fragment = new CourseListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        //inflate the courselist layout
        View v = inflater.inflate(R.layout.course_list_home, container, false);

        Toolbar toolbar =  v.findViewById(R.id.pager_toolbar);
        AppCompatActivity act = (AppCompatActivity) getActivity();
        act.setSupportActionBar(toolbar);
        ActionBar actionBar = act.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            actionBar.setTitle("Course List");
        }
        FragmentManager fm=getFragmentManager();

        pager=(ViewPager)v.findViewById(R.id.single_pager);
        tabs=(TabLayout) v.findViewById(R.id.course_tab_layout);
        tabs.setupWithViewPager(pager);


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
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }
}
