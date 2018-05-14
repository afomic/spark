package com.afomic.spark.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetupActivity extends AppCompatActivity {
    @BindView(R.id.spn_departments)
    Spinner departmentSpinner;


    private String selectedDepartment;
    private PreferenceManager mPreferenceManager;

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
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    @OnClick(R.id.btn_submit_department)
    public void selectDepartment(){

    }
}

