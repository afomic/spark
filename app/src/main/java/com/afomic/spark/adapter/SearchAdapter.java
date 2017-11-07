package com.afomic.spark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.afomic.spark.R;
import com.afomic.spark.activities.ConstitutionViewerActivity;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.ConstitutionData;
import com.afomic.spark.model.Constitution;
import com.afomic.spark.util.TextUtil;

import java.util.ArrayList;

/**
 * Created by afolabi michael on 04-Nov-16.
 *
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.myHolder> {
    String query;
    Context context;
    ConstitutionData data;
    int start,end;
    String[]articles={"Article 1: General Provision","Article 2: The Constitution","Article 3: The Executive Council","Article 4: The Legislation",
            "Article 5: Organs Of The Association","Article 6: Election","Article 7: Miscellaneous"};
    ArrayList<Constitution> searchArray;
    public SearchAdapter(Context c){
        data=ConstitutionData.get();
        context=c;
    }
    public void setString(String query){
        this.query=query;
        searchArray=getSearchResult();
        notifyDataSetChanged();
    }
    public ArrayList<Constitution> getSearchResult(){
        return data.search(query);
    }
    public Constitution getItem(int position){
        return searchArray.get(position);
    }


    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.search_card,null);
        return new myHolder(v);
    }

    @Override
    public void onBindViewHolder(myHolder holder, final int position) {
        final Constitution item=getItem(position);
        holder.articleName.setText(articles[item.getArticle()]);
        holder.sectionName.setText(item.getTitle());
        //strip the text of all the html tags
        String escapedString=getString(item.getContent());
        //get the begainining of the query
        start=getStart(escapedString.toLowerCase());
        end=getEnd(start);
        //if the query is found in the heading dont highlight else do
        if(start==-1){
            int secStart=getStart(item.getTitle().toLowerCase());
            end=getEnd(secStart);
            Log.d(Constants.TAG,item.getTitle());
            CharSequence text= TextUtil.highlightSection(secStart, end,item.getTitle());
            holder.sectionName.setText(text);
            holder.searchText.setText(getString(item.getContent()));
            query="";
        }else {
            CharSequence text= TextUtil.highlightText(start, end,escapedString);
            holder.searchText.setText(text);
        }
        holder.searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ConstitutionViewerActivity.class);
                intent.putExtra(Constants.SECTION,item.getSection());
                intent.putExtra(Constants.ARTICLE,item.getArticle());
                intent.putExtra(Constants.QUERY,query);
                intent.putExtra(Constants.TITLE,item.getTitle());
                intent.putExtra(Constants.SIZE,data.getConstitution(item.getArticle()).size());
                context.startActivity(intent);
            }
        });

    }
    public String getString(String content){
        return Html.fromHtml(content).toString();
    }

    public int getStart(String content){
        return content.indexOf(query.toLowerCase());
    }
    public int getEnd(int start){
        return start+ query.length();
    }


    @Override
    public int getItemCount() {
        return searchArray.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        TextView sectionName,articleName,searchText;
        public myHolder(View v){
            super(v);
            searchText=(TextView)v.findViewById(R.id.search_text);
            sectionName=(TextView)v.findViewById(R.id.search_section);
            articleName=(TextView)v.findViewById(R.id.search_article);
        }

    }
}
