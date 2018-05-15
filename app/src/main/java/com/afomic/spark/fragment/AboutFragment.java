package com.afomic.spark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.spark.R;

/**
 * this is the fragment to show the information about the department
 * Created by afomic on 18-Dec-16.
 */

public class AboutFragment extends Fragment {
    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate the about view
        View v = inflater.inflate(R.layout.about_page, container, false);
        //setup the toolbar
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.about_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM);
        collapsingToolbarLayout.setCollapsedTitleGravity(GravityCompat.START);
        AppCompatActivity act = (AppCompatActivity) getActivity();
        act.setSupportActionBar(toolbar);
        act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        act.getSupportActionBar().setTitle("About");
        return v;
    }

}
