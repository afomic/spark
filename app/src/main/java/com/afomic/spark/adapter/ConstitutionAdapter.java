package com.afomic.spark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.spark.R;
import com.afomic.spark.data.ConstitutionData;


/**
 * Created by afolabi michael on 17-Oct-16.
 *
 */
public class ConstitutionAdapter extends BaseExpandableListAdapter {
    String[]articles={"Article 1: General Provision","Article 2: The Constitution","Article 3: The Executive Council","Article 4: The Legislation",
            "Article 5: Organs Of The Association","Article 6: Election","Article 7: Miscellaneous"};
    Context context;
    ConstitutionData data;
    public ConstitutionAdapter(Context context) {
        this.context=context;
        data=ConstitutionData.get();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.getConstitution(groupPosition).size();
    }

    @Override
    public String  getGroup(int groupPosition) {
        return articles[groupPosition];
    }

    @Override
    public String  getChild(int groupPosition, int childPosition) {
        return data.getSection(groupPosition,childPosition).getTitle();
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
        convertView= LayoutInflater.from(context).inflate(R.layout.article_item,parent,false);
        TextView view=(TextView) convertView.findViewById(R.id.section_title);
        ImageView upDownImage=(ImageView) convertView.findViewById(R.id.down_up);
        if(isExpanded){
            upDownImage.setImageResource(R.drawable.ic_up);
        }else {
            upDownImage.setImageResource(R.drawable.ic_down);
        }
        view.setText(articles[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.section_item,parent,false);
        TextView title=(TextView)convertView.findViewById(R.id.section_title);
        TextView sectionNum=(TextView)convertView.findViewById(R.id.section_number);
        View background=convertView.findViewById(R.id.black_background);
        sectionNum.setText(""+(childPosition+1));
        title.setText(getChild(groupPosition, childPosition));
        if(isLastChild){
            background.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
