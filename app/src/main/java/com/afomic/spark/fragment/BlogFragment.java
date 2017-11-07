package com.afomic.spark.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.spark.BlogDetailActivity;
import com.afomic.spark.R;
import com.afomic.spark.adapter.BlogAdapter;
import com.afomic.spark.data.Constants;
import com.afomic.spark.model.BlogPost;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by afomic on 11/3/17.
 */

public class BlogFragment extends Fragment  {
    private BlogAdapter.BlogPostListener mPostListener=new BlogAdapter.BlogPostListener() {
        @Override
        public void OnFileBlogPostClick(String fileUrl, String filename) {
            Uri file_uri = Uri.parse(fileUrl);
            DownloadManager downloadManager=(DownloadManager)
                    getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(file_uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle("Spark Downloading " + filename);
            request.setDescription("Downloading " + filename);
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                    "/Spark/doc/"  + "/" + filename);


            long refid = downloadManager.enqueue(request);
        }

        @Override
        public void onBlogBlogPostClick(BlogPost BlogPost) {
            Intent intent=new Intent(getActivity(), BlogDetailActivity.class);
            intent.putExtra(Constants.EXTRA_BLOG,BlogPost);
            getActivity().startActivity(intent);


        }
    };
    public static BlogFragment newInstance(){
        return new BlogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_blog,container,false);
        initializeView(v);
        return v;
    }
    public void initializeView(View v){
        RecyclerView blogList =v.findViewById(R.id.rv_blog_list);
        ArrayList<BlogPost> mPosts=new ArrayList<>();
        BlogAdapter mBlogAdapter=new BlogAdapter(getContext(),mPosts,mPostListener);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        blogList.setAdapter(mBlogAdapter);
        blogList.setLayoutManager(mLayoutManager);
        initializeFirebase(mPosts,mBlogAdapter);

        //set up with firbase
    }
    public void initializeFirebase(final ArrayList<BlogPost> posts, final BlogAdapter adapter) {
        FirebaseDatabase.getInstance().getReference("blog")
                .child("nacoss")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        BlogPost post = dataSnapshot.getValue(BlogPost.class);
                        posts.add(0, post);
                        adapter.notifyItemInserted(0);

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }


}
