package com.afomic.spark.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.afomic.spark.R;
import com.afomic.spark.activities.ProfileActivity;
import com.afomic.spark.adapter.ProfileAdapter;
import com.afomic.spark.data.Constants;


/**
 * Created by afomic on 13-Dec-16.
 * this is use to render each of the profile option avaliable to the user
 * and this include the executive profiles parliamentarian and the lecturer profile
 */

public class ProfileFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile_layout,container,false);
        Toolbar toolbar=v.findViewById(R.id.toolbar);
        AppCompatActivity act=(AppCompatActivity)getActivity();
        act.setSupportActionBar(toolbar);
        ActionBar actionBar=act.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            actionBar.setTitle("Profile");
        }
        GridView profiles=(GridView) v.findViewById(R.id.profile_grid);
        profiles.setAdapter(new ProfileAdapter(getActivity()));
        profiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(Constants.TYPE,i);
                startActivity(intent);
            }
        });
        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }
}
