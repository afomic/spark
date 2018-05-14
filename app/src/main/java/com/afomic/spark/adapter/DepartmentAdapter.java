package com.afomic.spark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.afomic.spark.model.AccessToken;

import java.util.List;

public class DepartmentAdapter extends BaseAdapter {
    private Context mContext;
    private List<AccessToken> mAccessToken;

    public DepartmentAdapter(Context context, List<AccessToken> accessTokens) {
        mContext = context;
        mAccessToken = accessTokens;
    }

    @Override
    public int getCount() {
        if (mAccessToken != null) {
            return mAccessToken.size();
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
        return null;
    }
}
