package com.afomic.spark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afomic.spark.R;
import com.afomic.spark.model.AccessToken;

import java.util.List;

public class DepartmentAdapter extends BaseAdapter {
    private Context mContext;
    private List<AccessToken> mAccessTokens;

    public DepartmentAdapter(Context context, List<AccessToken> accessTokens) {
        mContext = context;
        mAccessTokens = accessTokens;
    }

    @Override
    public int getCount() {
        if (mAccessTokens != null) {
            return mAccessTokens.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccessToken token=mAccessTokens.get(position);
        DepartmentHolder departmentHolder;
        if(convertView==null){
            departmentHolder=new DepartmentHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_department,parent,false);
            departmentHolder.departmentNameTextView=convertView.findViewById(R.id.tv_department_name);
            convertView.setTag(departmentHolder);
        }else {
            departmentHolder=(DepartmentHolder) convertView.getTag();
        }
        departmentHolder.departmentNameTextView.setText(token.getDepartmentName());
        return convertView;
    }

    public class DepartmentHolder{
        TextView departmentNameTextView;
    }
}
