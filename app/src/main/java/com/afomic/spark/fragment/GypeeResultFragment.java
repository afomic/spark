package com.afomic.spark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.PreferenceManager;


/**
 * Created by afomic on 22-Oct-16.
 */

public class GypeeResultFragment extends Fragment {
    int option, semester, level;
    String gpa, cgpa;
    TextView mySemester, myLevel, myGpa, myCgpa;
    PreferenceManager preference;
    double totalPoint, totalUnit, previousTotalPoint, previousTotalUnit;

    public static GypeeResultFragment getInstance(int level, int semester, double previousTotalPoint
            , double previousTotalUnit, double totalPoint, double totalUnit, int option, String gpa, String cgpa) {
        GypeeResultFragment fragment = new GypeeResultFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(Constants.TOTAL_UNIT, totalUnit);
        bundle.putDouble(Constants.TOTAL_POINT, totalPoint);
        bundle.putDouble(Constants.PREVIOUS_TOTAL_POINT, previousTotalPoint);
        bundle.putDouble(Constants.PREVIOUS_TOTAL_UNIT, previousTotalUnit);
        bundle.putInt(Constants.SEMESTER, semester);
        bundle.putInt(Constants.LEVEL, level);
        bundle.putInt(Constants.OPTION, option);
        bundle.putString(Constants.GPA, gpa);
        bundle.putString(Constants.CGPA, cgpa);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        preference = new PreferenceManager(getActivity());
        totalPoint = arg.getDouble(Constants.TOTAL_POINT);
        totalUnit = arg.getDouble(Constants.TOTAL_UNIT);
        Log.d(Constants.TAG, "" + totalPoint + " " + totalUnit);
        previousTotalPoint = arg.getDouble(Constants.PREVIOUS_TOTAL_POINT);
        previousTotalUnit = arg.getDouble(Constants.PREVIOUS_TOTAL_UNIT);
        option = arg.getInt(Constants.OPTION);
        level = arg.getInt(Constants.LEVEL);
        semester = arg.getInt(Constants.SEMESTER);
        gpa = arg.getString(Constants.GPA);
        cgpa = arg.getString(Constants.CGPA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gypee_result, container, false);
        setHasOptionsMenu(true);

        myCgpa = (TextView) v.findViewById(R.id.result_cgpa);
        myGpa = (TextView) v.findViewById(R.id.result_gpa);
        mySemester = (TextView) v.findViewById(R.id.result_semester);
        myLevel = (TextView) v.findViewById(R.id.result_level);

        myCgpa.setText(cgpa);
        myLevel.setText(String.valueOf(level));
        mySemester.setText(String.valueOf(semester));
        myGpa.setText(gpa);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gypee_result, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_next) {
            next(level, semester);
            GypeeCalculateFragment fragment = GypeeCalculateFragment.getInstance(level, semester, option, totalPoint, totalUnit);
            getFragmentManager().beginTransaction().replace(R.id.gypee_container, fragment).commit();

        } else if (item.getItemId() == android.R.id.home) {
            GypeeCalculateFragment fragment = GypeeCalculateFragment.getInstance(level, semester, option, previousTotalPoint, previousTotalUnit);
            getFragmentManager().beginTransaction().replace(R.id.gypee_container, fragment).commit();
        } else if (item.getItemId() == R.id.menu_save) {
            next(level, semester);
            if (PreferenceManager.isLoggedIn()) {
                SetPasswordDialog dialog = SetPasswordDialog.getInstance(semester, level, option, totalPoint, totalUnit, Constants.UPDATE_DATA);
                dialog.show(getFragmentManager(), null);

            } else if (!preference.isThereASavedData()) {
                SetPasswordDialog dialog = SetPasswordDialog.getInstance(semester, level, option, totalPoint, totalUnit, Constants.SAVE_DATA);
                dialog.show(getFragmentManager(), null);
            }
        }
        return true;
    }

    public void next(int level, int semester) {
        if (semester == 1) {
            this.semester = semester + 1;
        } else {
            this.semester = semester - 1;
            this.level = level + 1;
        }
    }

}
