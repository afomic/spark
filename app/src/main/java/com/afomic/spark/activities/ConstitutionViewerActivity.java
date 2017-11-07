package com.afomic.spark.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.ConstitutionData;
import com.afomic.spark.fragment.ConstitutionDetailFragment;
import com.afomic.spark.util.ZoomOutTransformer;


/**
 * Created by afomic on 19-Oct-16.
 * this is the
 */
public class ConstitutionViewerActivity extends AppCompatActivity implements ConstitutionDetailFragment.constitutionCallback{
    int section,article,size;
    String query;
    ViewPager pager;
    Toolbar toolbar;
    ConstitutionData data;
    FragmentStatePagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constitution_detail);
        FragmentManager fm=getSupportFragmentManager();
        toolbar=(Toolbar)findViewById(R.id.pager_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pager=(ViewPager)findViewById(R.id.single_pager);
        pager.setOffscreenPageLimit(0);
        data=ConstitutionData.get();

        Bundle passedData=getIntent().getExtras();
        article=passedData.getInt(Constants.ARTICLE,0);
        section=passedData.getInt(Constants.SECTION,0);
        size=passedData.getInt(Constants.SIZE, 0);
        query=passedData.getString(Constants.QUERY,"");
        pager.setPageTransformer(true, new ZoomOutTransformer());
        showTitle(section);
        adapter=new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                ConstitutionDetailFragment fragment= ConstitutionDetailFragment.getInstance(article, position,query);
                query="";
                return fragment;
            }
            @Override
            public int getCount() {
                return size;
            }
        };
        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               showTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setCurrentItem(section);


    }

    @Override
    public void articleSelected(int section) {
        article=section;
        size=data.getConstitution(section).size();
        adapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ConstitutionDetailFragment.getInstance(article,position,"");
            }

            @Override
            public int getCount() {
                return size;
            }
        };
      pager.setAdapter(adapter);


    }
    public void showTitle(int position){
        String title= data.getSection(article,position).getTitle().toLowerCase();
        setTitle(title);
    }
}
