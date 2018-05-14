package com.afomic.spark.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.afomic.spark.R;
import com.afomic.spark.adapter.DepartmentAdapter;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.PreferenceManager;
import com.afomic.spark.model.AccessToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetupActivity extends AppCompatActivity {
    @BindView(R.id.spn_departments)
    Spinner departmentSpinner;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


    private String selectedDepartment;
    private PreferenceManager mPreferenceManager;
    private List<AccessToken> mAccessTokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(SetupActivity.this);
        mPreferenceManager=new PreferenceManager(this);

        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.EXTRA_ACCESS_TOKEN_REF)
                .orderByChild("used")
                .equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mProgressBar.setVisibility(View.GONE);
                        mAccessTokens=new ArrayList<>();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            mAccessTokens.add(snapshot.getValue(AccessToken.class));
                        }
                        DepartmentAdapter departmentAdapter=new DepartmentAdapter(SetupActivity.this,mAccessTokens);
                        departmentSpinner.setAdapter(departmentAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AccessToken accessToken=mAccessTokens.get(position);
                selectedDepartment=accessToken.getAssociationName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AccessToken accessToken=mAccessTokens.get(0);
                selectedDepartment=accessToken.getAssociationName();
            }
        });
    }
    @OnClick(R.id.btn_submit_department)
    public void selectDepartment(){
        mPreferenceManager.setDepartmentName(selectedDepartment);
        startActivity(new Intent(SetupActivity.this,MainActivity.class));
    }
}

