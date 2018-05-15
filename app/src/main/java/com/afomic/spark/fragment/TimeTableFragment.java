package com.afomic.spark.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afomic.spark.R;
import com.afomic.spark.adapter.TimeTableAdapter;
import com.afomic.spark.util.TimeTableConvert;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;


/**
 * Created by afomic on 23-Oct-16.
 */
public class TimeTableFragment extends Fragment implements TimeTableDetailDialog.ChangeListener, SetClassDialog.CourseListener {
    TimeTableAdapter adapter;
    TimeTableConvert save;
    private final int REQ_CODE = 101;

    public static TimeTableFragment getInstance() {
        return new TimeTableFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        adapter = new TimeTableAdapter(getActivity(), getFragmentManager(), TimeTableFragment.this);
        super.onCreate(savedInstanceState);
        save = new TimeTableConvert(getActivity());
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timetable_header, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        AppCompatActivity act = (AppCompatActivity) getActivity();
        act.setSupportActionBar(toolbar);
        ActionBar actionBar = act.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            actionBar.setTitle("Gypee");
        }
        RecyclerView timeTableList = (RecyclerView) v.findViewById(R.id.lv_time_table);
        timeTableList.setLayoutManager(new LinearLayoutManager(getActivity()));
        timeTableList.setAdapter(adapter);
        return v;
    }

    @Override
    public void onModify(int time, int date) {
        SetClassDialog dialog = SetClassDialog.getInstance(time, date, "update");
        dialog.setTargetFragment(this, 21221);
        dialog.show(getFragmentManager(), null);
    }

    @Override
    public void onDelete() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void classAdded() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.time_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.time_share:
                checkPermission();
                Intent i = new Intent(Intent.ACTION_SEND);
                File dir = new File(Environment.getExternalStorageDirectory(), "nacoss");
                File f = new File(dir, "TimeTable.txt");
                if (f.isFile()) {
                    i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                    i.setType("text/plain");
                    Log.e(TAG, "onOptionsItemSelected: " + f.getPath());
                    startActivity(Intent.createChooser(i, "Send TimeTable..."));
                }
                break;
            case R.id.time_download:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/plain");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, REQ_CODE);
                }

                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE && resultCode == getActivity().RESULT_OK) {
            Uri fileUri = data.getData();
            Log.d(TAG, fileUri.toString());
            String timetable = getTimeTable(fileUri);
            try {
                JSONArray jsonArray = new JSONArray(timetable);
                save.importTimeTable(jsonArray);
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error parsing file", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
        adapter.notifyDataSetChanged();
    }

    public void saveData() {
        String data = null;
        try {
            data = save.toJsonArray().toString();
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Unsuccessful", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if (data != null) {
            save.saveData(data);
        }


    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("the permission is required to save TimeTable");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                            saveData();
                        }
                    });
                    builder.create().show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            } else {
                saveData();
            }
        } else {
            saveData();
        }
    }

    public String getTimeTable(Uri uri) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(getActivity().getContentResolver().openInputStream(uri)));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
