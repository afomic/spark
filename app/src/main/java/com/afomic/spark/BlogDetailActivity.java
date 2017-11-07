package com.afomic.spark;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;


import com.afomic.spark.adapter.BlogDisplayAdapter;
import com.afomic.spark.data.Constants;
import com.afomic.spark.model.BlogElement;
import com.afomic.spark.model.BlogPost;
import com.afomic.spark.util.ElementParser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlogDetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<BlogElement>>{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_blog)
    RecyclerView blogView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    BlogPost mBlogPost;

    private static final String PARAM_BLOG_HTML="html";

    private static final int BLOG_ELEMENT_LOADER_ID=101;

    LoaderManager mLoadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
        }
        mBlogPost=getIntent().getParcelableExtra(Constants.EXTRA_BLOG);

        //parse the html in the background
        mLoadManager=getSupportLoaderManager();
        Bundle args=new Bundle();
        args.putString(PARAM_BLOG_HTML,mBlogPost.getBody());
        Loader<ArrayList<BlogElement>> mBlogLoader=mLoadManager.getLoader(BLOG_ELEMENT_LOADER_ID);
        if(mBlogLoader==null){
            mLoadManager.initLoader(BLOG_ELEMENT_LOADER_ID,args,this);
        }else {
            mLoadManager.restartLoader(BLOG_ELEMENT_LOADER_ID,args,this);
        }

    }
    public static class parseBlogElement extends AsyncTaskLoader<ArrayList<BlogElement>>{
        private Bundle param;
        public parseBlogElement(Context context,Bundle param){
            super(context);
            this.param=param;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public ArrayList<BlogElement> loadInBackground() {
            return ElementParser.fromHtml(param.getString(PARAM_BLOG_HTML));
        }
    }

    @Override
    public Loader<ArrayList<BlogElement>> onCreateLoader(int id, Bundle args) {
        return new parseBlogElement(this,args);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<BlogElement>> loader, ArrayList<BlogElement> data) {
        mProgressBar.setVisibility(View.GONE);
        BlogDisplayAdapter adapter=new BlogDisplayAdapter(BlogDetailActivity.this,data);
        blogView.setLayoutManager(new LinearLayoutManager(this));
        blogView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<BlogElement>> loader) {

    }
}
