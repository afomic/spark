package com.afomic.spark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.spark.R;


/**
 * Created by afomic on 18-Oct-16.
 *
 */
public class ExpendableListAdapter  extends BaseExpandableListAdapter{
    Context context;
    String[] parent={"Electives","Part One","Part Two","Part Three","Part Four","Part Five"};
    String[] child={"Economics","Engineering","Mathematics"};
    public ExpendableListAdapter(Context context) {
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return parent.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.length;
    }

    @Override
    public String getGroup(int groupPosition) {
        return parent[groupPosition];
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return child[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.part_item,parent,false);
        TextView textView=(TextView) convertView.findViewById(R.id.part_title);
        textView.setText(getGroup(groupPosition));
        ImageView dot=(ImageView) convertView.findViewById(R.id.dot);
        ImageView open=(ImageView) convertView.findViewById(R.id.down_up);
        if(isExpanded){
            dot.setImageResource(R.drawable.circle);
            open.setImageResource(R.drawable.ic_up);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView=new TextView(context);
        textView.setText(getChild(groupPosition,childPosition));
        textView.setPadding(74,10,10,10);
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
