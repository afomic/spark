package com.afomic.spark.fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.afomic.spark.BlogDetailActivity;
import com.afomic.spark.R;
import com.afomic.spark.adapter.PostAdapter;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.PreferenceManager;
import com.afomic.spark.model.BlogPost;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by afomic on 11/14/17.
 *
 */

public class PostFragment extends Fragment implements PostAdapter.BlogPostListener{
    @BindView(R.id.rv_post_list)
    RecyclerView postRecyclerView;
    @BindView(R.id.blog_toolbar)
    Toolbar blogToolbar;
    ArrayList<BlogPost> mPostList;
    PostAdapter mAdapter;

    Unbinder mUnbinder;
    private static Map<Long,String> downloadRef=new HashMap<>();
    public static PostFragment newInstance(){
        return new PostFragment();
    }
    private PreferenceManager mPreferenceManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostList=new ArrayList<>();
        mPreferenceManager=new PreferenceManager(getActivity());
        requestPermission();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_post,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        AppCompatActivity act=(AppCompatActivity)getActivity();
        act.setSupportActionBar(blogToolbar);
        act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        act.getSupportActionBar().setTitle("Posts");
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getActivity().registerReceiver(new DownloadBroadcastReciever(), filter);



        mAdapter=new PostAdapter(getActivity(),mPostList,this);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        postRecyclerView.setLayoutManager(mLayoutManager);
        postRecyclerView.setAdapter(mAdapter);


        DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance()
                .getReference("posts")
                .child(mPreferenceManager.getDepartmentName());
        mDatabaseReference.orderByChild("status")
                .equalTo(Constants.STATUS_APPROVED)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        BlogPost mPost=dataSnapshot.getValue(BlogPost.class);
                        mPostList.add(0,mPost);
                        mAdapter.notifyItemInserted(0);
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

        return v;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();

    }
    @Override
    public void OnFileBlogPostClick(BlogPost blogPost) {
        updateStats(blogPost.getId());
        if(downloadRef.containsValue(blogPost.getId())){// file is already been downloaded
            Toast.makeText(getActivity(),"File is Already been downloaded",Toast.LENGTH_SHORT).show();
        }else {

            File direct = new File(Environment.getExternalStorageDirectory()
                    + "/Spark/doc");
            if (!direct.exists()) {
                direct.mkdirs();
            }
            Uri file_uri = Uri.parse(blogPost.getFileUrl());
            DownloadManager downloadManager=(DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(file_uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle("Downloading " + blogPost.getTitle());
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalPublicDir("Spark/doc", blogPost.getTitle());

            long refid = downloadManager.enqueue(request);
            downloadRef.put(refid,blogPost.getId());
        }

    }

    @Override
    public void onBlogBlogPostClick(BlogPost BlogPost) {
        Intent intent=new Intent(getActivity(),BlogDetailActivity.class);
        intent.putExtra(Constants.EXTRA_BLOG_POST,BlogPost);
        startActivity(intent);

    }

    public void requestPermission(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[] {
                                android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },100);
            }
        }


    }
    public void updateStats(String postId){
        FirebaseDatabase.getInstance()
                .getReference("recommendation")
                .child(postId)
                .child(mPreferenceManager.getUserId())
                .setValue(true);

    }
    public static class DownloadBroadcastReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            String id=downloadRef.get(referenceId);
            if(id!=null){
                Toast.makeText(context,"File downloaded",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
