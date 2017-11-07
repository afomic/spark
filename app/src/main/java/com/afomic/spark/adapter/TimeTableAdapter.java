package com.afomic.spark.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.afomic.spark.R;
import com.afomic.spark.data.TimeTableData;
import com.afomic.spark.fragment.SetClassDialog;
import com.afomic.spark.fragment.TimeTableDetailDialog;
import com.afomic.spark.fragment.TimeTableFragment;
import com.afomic.spark.model.TimeTableClass;

import java.util.ArrayList;

/**
 * Created by afomic on 23-Oct-16.
 * this is the adapter that will be responsible for populate the time table list view
 * it will read data from the database when the application is lunched
 * it will write to the database when the user try to enter new class
 */
public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.myHolder>{
    Context context;
    private TimeTableData dbData;
    private FragmentManager fm;
    TimeTableFragment parent;
    public TimeTableAdapter(Context context, FragmentManager fm, TimeTableFragment ps){
        this.context=context;
        this.fm=fm;
        parent=ps;
        dbData=new TimeTableData(context);
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.timetable_item,parent,false);
        return new myHolder(v);
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
        holder.time.setText(getTime(position));
        holder.mondayClassLayout.setOnClickListener(new onItemClick(position, 0));
        holder.tuesdayClassLayout.setOnClickListener(new onItemClick(position,1));
        holder.wednesdayClassLayout.setOnClickListener(new onItemClick(position,2));
        holder.thursdayClassLayout.setOnClickListener(new onItemClick(position, 3));
        holder.fridayClassLayout.setOnClickListener(new onItemClick(position, 4));
        TimeTableClass emptyClass=new TimeTableClass(0,"","", Color.WHITE,0,"");
        setValues(holder.mondayClassLayout,emptyClass);
        setValues(holder.tuesdayClassLayout,emptyClass);
        setValues(holder.wednesdayClassLayout,emptyClass);
        setValues(holder.thursdayClassLayout,emptyClass);
        setValues(holder.fridayClassLayout,emptyClass);
        populateList(position,holder);
    }

    @Override
    public int getItemCount() {
        return 12;
    }


    public class myHolder extends RecyclerView.ViewHolder{
        LinearLayout mondayClassLayout,tuesdayClassLayout,wednesdayClassLayout,thursdayClassLayout,fridayClassLayout;
        TextView time;
        private myHolder(View itemView) {
            super(itemView);
            time=(TextView) itemView.findViewById(R.id.tv_time);
            mondayClassLayout=(LinearLayout) itemView.findViewById(R.id.ll_monday_class);
            tuesdayClassLayout=(LinearLayout) itemView.findViewById(R.id.ll_tuesday_class);
            wednesdayClassLayout=(LinearLayout) itemView.findViewById(R.id.ll_wednesday_class);
            thursdayClassLayout=(LinearLayout) itemView.findViewById(R.id.ll_thursday_class);
            fridayClassLayout=(LinearLayout) itemView.findViewById(R.id.ll_friday_class);
            
        }

       

    }
    private String getTime(int position){
        int startTime=position+7;
        if(startTime>12){
            return String.valueOf(startTime%12);
        }
        return String.valueOf(startTime);
    }

    private  class onItemClick implements View.OnClickListener{
        int position,date;
        private onItemClick(int position,int date){
            this.position=position;
            this.date=date;
        }
        @Override
        public void onClick(View v) {
            TimeTableClass prev=dbData.getClass(position,date);
            if(prev==null){
                SetClassDialog dialog=SetClassDialog.getInstance(position, date,"add");
                dialog.setTargetFragment(parent,243);
                dialog.show(fm, null);

            }else {
                TimeTableDetailDialog dialog=TimeTableDetailDialog.getInstance(prev);
                dialog.setTargetFragment(parent,247);
                dialog.show(fm,null);
            }
        }
    }
    private void populateList(int position,myHolder holder){
        ArrayList<TimeTableClass> mClasses=dbData.getTimeTable(position);
        if(mClasses!=null&&mClasses.size()!=0){
            for(TimeTableClass item:mClasses){
                switch (item.getDate()){
                    case 0:
                        setValues(holder.mondayClassLayout,item);
                        break;
                    case 1:
                        setValues(holder.tuesdayClassLayout,item);
                        break;
                    case 2:
                        setValues(holder.wednesdayClassLayout,item);
                        break;
                    case 3:
                        setValues(holder.thursdayClassLayout,item);
                        break;
                    case 4:
                        setValues(holder.fridayClassLayout,item);
                        break;
                }
            }
        }

    }
  
    private  void setValues(LinearLayout layout,TimeTableClass item){
        TextView className=(TextView)layout.getChildAt(0);
        TextView classVenue=(TextView)layout.getChildAt(1);
        className.setText(item.getName());
        classVenue.setText(item.getVenue());
        layout.setBackgroundColor(item.getColor());
    }



}
