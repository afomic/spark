package com.afomic.spark.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.afomic.spark.R;
import com.afomic.spark.adapter.HelperAdapter;


/**
 * Created by afomic on 10-Dec-16.
 */

public class HelpFragment extends Fragment {

    public static HelpFragment newInstance(){
        return new HelpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.help,container,false);
        ExpandableListView helpList= v.findViewById(R.id.help_expandable);
        Toolbar mToolbar=(Toolbar) v.findViewById(R.id.help_toolbar);
        AppCompatActivity act=(AppCompatActivity)getActivity();
        act.setSupportActionBar(mToolbar);
        act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        act.getSupportActionBar().setTitle("Help");
        HelperAdapter adapter=new HelperAdapter(getActivity());
        helpList.setAdapter(adapter);
        return v;
    }
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
