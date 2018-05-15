package com.afomic.spark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.afomic.spark.R;
import com.afomic.spark.adapter.PersonAdapter;
import com.afomic.spark.data.PreferenceManager;
import com.afomic.spark.model.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by afomic on 17-Oct-16.
 */
public class PersonViewerFragment extends Fragment {
    private static final String BUNDLE_TYPE = "type";
    RecyclerView grid;
    int type;
    private ArrayList<Profile> mProfiles;
    private DatabaseReference profileRef;
    private PersonAdapter adapter;
    private PreferenceManager mPreferenceManager;
    private ProgressBar mProgressBar;
    private LinearLayout mEmptyView;


    public static PersonViewerFragment getInstance(int type) {
        PersonViewerFragment fragment = new PersonViewerFragment();
        Bundle arg = new Bundle();
        arg.putInt(BUNDLE_TYPE, type);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(BUNDLE_TYPE);
        mPreferenceManager = new PreferenceManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_viewer, container, false);
        grid = view.findViewById(R.id.person_grid);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mEmptyView = view.findViewById(R.id.empty_list_view);
        int screenWidth = getResources().getConfiguration().screenWidthDp;
        int numberOfRows = screenWidth / 140;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numberOfRows);
        grid.setLayoutManager(mLayoutManager);

        grid.setItemAnimator(new DefaultItemAnimator());

        mProfiles = new ArrayList<>();
        adapter = new PersonAdapter(getActivity(), mProfiles);

        grid.setAdapter(adapter);
        profileRef = FirebaseDatabase.getInstance().getReference("profile")
                .child(mPreferenceManager.getDepartmentName());
        profileRef.orderByChild("type")
                .equalTo(type)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mProfiles.clear();
                        mProgressBar.setVisibility(View.GONE);
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Profile item = snapshot.getValue(Profile.class);
                            mProfiles.add(item);
                        }
                        if (mProfiles.size() > 0) {
                            adapter.notifyDataSetChanged();
                            mEmptyView.setVisibility(View.GONE);
                        } else {
                            mEmptyView.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        return view;
    }

}
