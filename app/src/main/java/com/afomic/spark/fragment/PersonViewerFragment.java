package com.afomic.spark.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.spark.R;
import com.afomic.spark.adapter.PersonAdapter;
import com.afomic.spark.data.Constants;


/**
 * Created by afomic on 17-Oct-16.
 */
public class PersonViewerFragment extends Fragment {
    RecyclerView grid;
    String type;

    public static PersonViewerFragment getInstance(String type){
        PersonViewerFragment fragment=new PersonViewerFragment();
        Bundle arg=new Bundle();
        arg.putString(Constants.TYPE,type);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getArguments().getString(Constants.TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.person_viewer,container,false);
        grid=(RecyclerView) view.findViewById(R.id.person_grid);
        int screenWidth= getResources().getConfiguration().screenWidthDp;
        int numberOfRows=screenWidth/140;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),numberOfRows);
        grid.setLayoutManager(mLayoutManager);



        grid.setItemAnimator(new DefaultItemAnimator());


//        final PersonAdapter adapter=new PersonAdapter(getContext(),type);
//        grid.setAdapter(adapter);

        return view;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
