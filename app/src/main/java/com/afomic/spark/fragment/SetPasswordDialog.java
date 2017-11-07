package com.afomic.spark.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afomic.spark.R;
import com.afomic.spark.activities.GypeeActivity;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.PreferenceManager;


/**
 * Created by afomic on 22-Oct-16.
 */
public class SetPasswordDialog extends DialogFragment {
    int semester,level,option,action;
    double totalUnit,totalPoint;
    EditText password,passWordAgain;
    TextView instruction;
    PreferenceManager preference;
    public static SetPasswordDialog getInstance(int semester,int level,int option,double totalPoint,double totalUnit,int action){
        SetPasswordDialog dialog=new SetPasswordDialog();
        Bundle args=new Bundle();
        args.putInt(Constants.SEMESTER,semester);
        args.putInt(Constants.LEVEL,level);
        args.putInt(Constants.OPTION,option);
        args.putDouble(Constants.TOTAL_POINT,totalPoint);
        args.putDouble(Constants.TOTAL_UNIT,totalUnit);
        args.putInt(Constants.ACTION,action);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        semester=bundle.getInt(Constants.SEMESTER);
        level=bundle.getInt(Constants.LEVEL);
        option=bundle.getInt(Constants.OPTION);
        totalPoint=bundle.getDouble(Constants.TOTAL_POINT);
        totalUnit=bundle.getDouble(Constants.TOTAL_UNIT);
        action=bundle.getInt(Constants.ACTION);
        preference=new PreferenceManager(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.set_password,null);
        password=(EditText)v.findViewById(R.id.edt_password);
        passWordAgain=(EditText) v.findViewById(R.id.edt_confirm_password);
        instruction=(TextView) v.findViewById(R.id.tv_instruction);
        builder.setView(v);
        switch (action) {
            case Constants.SAVE_DATA:
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass = password.getText().toString();
                        String passAgain = passWordAgain.getText().toString();
                        if (!pass.equals("") && pass.equals(passAgain)) {
                            PreferenceManager.setLoggedIn();
                            preference.setLevel(level);
                            preference.setSemester(semester);
                            preference.setPassword(pass);
                            preference.setTotalPoint((int) totalPoint);
                            preference.setTotalUnit((int) totalUnit);
                            preference.setOption(option);
                            Toast.makeText(getActivity(), "Detail Saved Successfully", Toast.LENGTH_SHORT).show();
                        }
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });
                break;
            case Constants.LOAD_DATA:
                passWordAgain.setVisibility(View.GONE);
                instruction.setText("Restore Data");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass = password.getText().toString();
                        if (pass.equals(preference.getPassword())) {
                            PreferenceManager.setLoggedIn();
                             sendData();
                        } else {
                            Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.setNeutralButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preference.setPassword("none");
                        PreferenceManager.setLoggedOut();
                    }
                });
                break;
            case Constants.UPDATE_DATA:
                instruction.setText("Update Your Data");
                password.setVisibility(View.GONE);
                passWordAgain.setVisibility(View.GONE);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preference.setLevel(level);
                        preference.setSemester(semester);
                        preference.setTotalPoint((int) totalPoint);
                        preference.setTotalUnit((int) totalUnit);
                        preference.setOption(option);
                        Toast.makeText(getActivity(), "Detail Updated Successfully", Toast.LENGTH_SHORT).show();
                        Log.d(Constants.TAG,"level "+level+" smes "+semester+" tp " +totalPoint+" total Unit "+totalUnit+" total point"+totalPoint);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });

                break;
        }


        return builder.create();
    }
    public void sendData(){
        level = preference.getLevel();
        semester = preference.getSemester();
        option = preference.getOption();
        totalUnit = preference.getTotalUnit();
        totalPoint = preference.getTotalPoint();
        Log.d(Constants.TAG,"level "+level+" smes "+semester+" tp " +totalPoint+" total Unit "+totalUnit+" total point"+totalPoint);
        Intent intent = new Intent(getActivity(), GypeeActivity.class);
        intent.putExtra(Constants.LEVEL, level);
        intent.putExtra(Constants.SEMESTER, semester);
        intent.putExtra(Constants.OPTION, option);
        intent.putExtra(Constants.TOTAL_UNIT, (int)totalUnit);
        intent.putExtra(Constants.TOTAL_POINT,(int) totalPoint);
        getActivity().startActivity(intent);
    }
}
