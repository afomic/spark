package com.afomic.spark.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afomic.spark.R;
import com.afomic.spark.adapter.ArticleListAdapter;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.ConstitutionData;
import com.afomic.spark.util.TextUtil;

/**
 * Created by afomic on 22-Oct-16.
 *
 */
public class ConstitutionDetailFragment extends Fragment {
    int article,section,start;
    String query;
    TextView articleName;
    boolean isArticleListVisible=false;
    constitutionCallback callback;
    TextView sectionName,constitution;
    ImageView openAndClose;
    ListView articleList;
    ScrollView scrollView;
    public interface constitutionCallback{
        public void articleSelected(int section);
    }
    public static ConstitutionDetailFragment getInstance(int article,int section,String query){
        ConstitutionDetailFragment fragment=new ConstitutionDetailFragment();
        Bundle arg=new Bundle();
        arg.putInt(Constants.ARTICLE, article);
        arg.putInt(Constants.SECTION, section);
        arg.putString(Constants.QUERY,query);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        setHasOptionsMenu(true);
        article=bundle.getInt(Constants.ARTICLE);
        section=bundle.getInt(Constants.SECTION);
        query=bundle.getString(Constants.QUERY,"");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.constitution_detail,container,false);
        constitution  =(TextView) v.findViewById(R.id.tv_constitution_detail);
        articleName=(TextView) v.findViewById(R.id.article_btn);
        sectionName=(TextView) v.findViewById(R.id.section_name);
        articleList=(ListView) v.findViewById(R.id.article_list);
        openAndClose=(ImageView) v.findViewById(R.id.opem_close);
        scrollView=(ScrollView) v.findViewById(R.id.scroll);

        //set the constitution on the page
        /*
        *set an onclick listener on the textview to make the list view visible
        * when its pressed
         */

        articleList.setAdapter(new ArticleListAdapter(getActivity(),article));
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.articleSelected(position);

            }
        });



        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isArticleListVisible){
                    articleList.setVisibility(View.VISIBLE);
                    openAndClose.setImageResource(R.drawable.ic_up);
                    isArticleListVisible=true;
                }else {
                    articleList.setVisibility(View.GONE);
                    openAndClose.setImageResource(R.drawable.ic_down);
                    isArticleListVisible=false;
                }

            }
        };
        articleName.setOnClickListener(listener);
        openAndClose.setOnClickListener(listener);
        ConstitutionData data=ConstitutionData.get();
        articleName.setText("Article "+(article +1));
        sectionName.setText("Section "+(section+1));
        String con=data.getSection(article,section).getContent();
        CharSequence span=Html.fromHtml(con);

        if(!query.equals("")) {//check to see if the content of the constitution is to be highlighted
            String lower=con.toLowerCase();
            start = Html.fromHtml(lower).toString().indexOf(query.toLowerCase());
            int end = start + query.length();
            constitution.setText(TextUtil.highLight(start, end, span));
            new Handler().post(new Runnable() {
                @Override
                public void run() {

                    scrollView.smoothScrollBy(0, start);
                }
            });

        }else {
            constitution.setText(span);
        }
        //make the first the present article cyan so as to make it more interactive

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback=(constitutionCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback=null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
