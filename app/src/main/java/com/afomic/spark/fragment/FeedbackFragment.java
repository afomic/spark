package com.afomic.spark.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afomic.spark.R;
import com.afomic.spark.adapter.FeedBackAdapter;


/**
 * Created by afomic on 27-Dec-16.
 */

public class FeedbackFragment extends Fragment {
    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feedback, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.feed_toolbar);
        AppCompatActivity act = (AppCompatActivity) getActivity();
        act.setSupportActionBar(toolbar);
        act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        act.getSupportActionBar().setTitle("Feedback");
        FeedBackAdapter adapter = new FeedBackAdapter(getActivity());
        ListView feedbacks = (ListView) v.findViewById(R.id.feedback_list);
        feedbacks.setAdapter(adapter);

        feedbacks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                switch (i) {
                    case 0:
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri webpage = Uri.parse("https://www.facebook.com/groups/154245617978681/");
                        intent.setData(webpage);
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        }

                        break;
                    case 1:
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri web = Uri.parse("https://www.instagram.com/nacoss_oau/");
                        intent.setData(web);
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:oaunacoss@gmail.com")); // only email apps should handle this

                        intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack on Nacoss app");
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        }
                }

            }
        });

        return v;
    }

}
