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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.afomic.spark.R;
import com.afomic.spark.activities.GypeeActivity;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.PreferenceManager;


/**
 * Created by afomic on 17-Oct-16.
 */
public class GypeeFragment extends Fragment {
    RadioButton hundred, twoHundred, threeHundred, fourHundred, fiveHundred, firstSemester, secondSemester;
    int level, semester, option;
    PreferenceManager preference;
    Spinner optionSpinner;

    public static GypeeFragment getInstance() {
        return new GypeeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            level = savedInstanceState.getInt(Constants.LEVEL);
            semester = savedInstanceState.getInt(Constants.SEMESTER);
            option = savedInstanceState.getInt(Constants.OPTION);
        }

        preference = new PreferenceManager(getActivity());
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gypee_home, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity act = (AppCompatActivity) getActivity();
        act.setSupportActionBar(toolbar);
        ActionBar actionBar = act.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            actionBar.setTitle("Gypee");
        }

        hundred = (RadioButton) view.findViewById(R.id.rb_level_hundred);
        twoHundred = (RadioButton) view.findViewById(R.id.rb_level_two_hundred);
        threeHundred = (RadioButton) view.findViewById(R.id.rb_level_three_hundred);
        fourHundred = (RadioButton) view.findViewById(R.id.rb_level_four_hundred);
        fiveHundred = (RadioButton) view.findViewById(R.id.rb_level_five_hundred);
        firstSemester = (RadioButton) view.findViewById(R.id.rb_semester_first);
        secondSemester = (RadioButton) view.findViewById(R.id.rb_semester_second);
        optionSpinner = (Spinner) view.findViewById(R.id.sp_quick);

        //setting action listener to each of the widget
        hundred.setOnCheckedChangeListener(new RbListner());
        threeHundred.setOnCheckedChangeListener(new RbListner());
        twoHundred.setOnCheckedChangeListener(new RbListner());
        fourHundred.setOnCheckedChangeListener(new RbListner());
        fiveHundred.setOnCheckedChangeListener(new RbListner());
        firstSemester.setOnCheckedChangeListener(new RbListner());
        secondSemester.setOnCheckedChangeListener(new RbListner());

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.option, android.R.layout.simple_selectable_list_item);
        optionSpinner.setAdapter(spinnerAdapter);
        optionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_gype_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_gypee_mark:
                if (checkFields()) {
                    Intent intent = new Intent(getActivity(), GypeeActivity.class);
                    intent.putExtra(Constants.OPTION, option);
                    intent.putExtra(Constants.LEVEL, level);
                    intent.putExtra(Constants.SEMESTER, semester);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "please fill the fields appropriately", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_gypee_restore:
                if (preference.isThereASavedData()) {
                    SetPasswordDialog dialog = SetPasswordDialog.getInstance(0, 0, 0, 0, 0, Constants.LOAD_DATA);
                    dialog.show(getFragmentManager(), null);
                } else {
                    Toast.makeText(getActivity(), "No Saved Data", Toast.LENGTH_SHORT).show();
                }

        }
        return true;
    }

    public boolean checkFields() {
        if (option == 0) {
            return false;
        } else if (semester == 0) {
            return false;
        } else if (level == 0) {
            return false;
        }
        return true;
    }

    public class RbListner implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.rb_level_hundred:
                    if (isChecked) {
                        level = 1;
                    }
                    break;
                case R.id.rb_level_two_hundred:
                    if (isChecked) {
                        level = 2;
                    }
                    break;
                case R.id.rb_level_three_hundred:
                    if (isChecked) {
                        level = 3;
                    }
                    break;
                case R.id.rb_level_four_hundred:
                    if (isChecked) {
                        level = 4;
                    }
                    break;
                case R.id.rb_level_five_hundred:
                    if (isChecked) {
                        level = 5;
                    }
                    break;
                case R.id.rb_semester_first:
                    if (isChecked) {
                        semester = 1;
                    }
                    break;
                case R.id.rb_semester_second:
                    if (isChecked) {
                        semester = 2;
                    }
                    break;

            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.LEVEL, level);
        outState.putInt(Constants.SEMESTER, semester);
        outState.putInt(Constants.OPTION, option);
    }
}
