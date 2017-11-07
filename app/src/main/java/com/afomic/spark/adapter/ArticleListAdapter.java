package com.afomic.spark.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afomic.spark.R;


/**
 * Created by afomic on 14-Nov-16.
 */
public class ArticleListAdapter extends BaseAdapter {
    String[]articles={"Article 1: General Provision","Article 2: The Constitution","Article 3: The Executive Council","Article 4: The Legislation",
            "Article 5: Organs Of The Association","Article 6: Election","Article 7: Miscellaneous"};
    Context context;
    int article;
    public ArticleListAdapter(Context context,int article){
        this.context=context;
        this.article=article;
    }
    @Override
    public int getCount() {
        return articles.length;
    }

    @Override
    public String getItem(int position) {
        return articles[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.aryicle_menu,parent,false);
        if(position==article){
            convertView.setBackgroundColor(Color.argb(214,187,222,251));
        }
        TextView view=(TextView) convertView.findViewById(R.id.article_menu);
        view.setText(getItem(position));
        return convertView;
    }
}
