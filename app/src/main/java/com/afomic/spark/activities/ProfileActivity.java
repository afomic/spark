package com.afomic.spark.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.fragment.PersonViewerFragment;


public class ProfileActivity extends AppCompatActivity {
    String[] titles={"Executive","Parliament","Lecturer"};
    String[] types={Constants.EXCO,Constants.PARLIAMENT,Constants.LECTURER};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar mToolbar=(Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(mToolbar);
        TabLayout tabs=(TabLayout) findViewById(R.id.profile_tab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager pager =(ViewPager) findViewById(R.id.profile_pager);
        setTitle("Profiles");
        pager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PersonViewerFragment.getInstance(types[position]);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabs.setupWithViewPager(pager);
        int position=getIntent().getIntExtra(Constants.TYPE,0);
        pager.setCurrentItem(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
