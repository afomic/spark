package com.afomic.spark.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afomic.spark.R;
import com.afomic.spark.adapter.navAdapter;
import com.afomic.spark.data.Constants;
import com.afomic.spark.data.GypeeCourseData;
import com.afomic.spark.data.PreferenceManager;
import com.afomic.spark.fragment.ConstitutionFragment;
import com.afomic.spark.fragment.CourseListFragment;
import com.afomic.spark.fragment.FeedbackFragment;
import com.afomic.spark.fragment.GypeeFragment;
import com.afomic.spark.fragment.HelpFragment;
import com.afomic.spark.fragment.PostFragment;
import com.afomic.spark.fragment.ProfileFragment;
import com.afomic.spark.fragment.TimeTableFragment;


public class MainActivity extends AppCompatActivity {
    FragmentManager fm;
    DrawerLayout drawerLayout;
    ListView navBar;
    LinearLayout layout;
    int privPosition;
    navAdapter adapter;
    boolean firstRun=true;
    private PreferenceManager preferenceManager;
    int pos=0;
    int[] imageId={R.drawable.home, R.drawable.help,R.drawable.feedback,R.drawable.about,
            R.drawable.about,R.drawable.about,R.drawable.about,R.drawable.about,R.drawable.about};

    int[] pink={R.drawable.home_click,R.drawable.help_blue,R.drawable.feedback_click,R.drawable.about,
            R.drawable.info_pink,R.drawable.about,R.drawable.about,R.drawable.about,R.drawable.about};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface roboto = Typeface.createFromAsset(getAssets(),"font/Lato-Regular.ttf");
        setContentView(R.layout.activity_main);
        preferenceManager=new PreferenceManager(MainActivity.this);
        drawerLayout=(DrawerLayout) findViewById(R.id.home);
        GypeeCourseData.addData(this);
        fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.main_container);
        if(fragment==null){
            PostFragment frag=PostFragment.newInstance();
            fm.beginTransaction().add(R.id.main_container,frag).commit();
        }
        if(savedInstanceState!=null){
            pos=savedInstanceState.getInt(Constants.POSITION);
        }


        //initialize the drawer layout

        navBar=(ListView) findViewById(R.id.nav_list);
        adapter=new navAdapter(this);
        navBar.setAdapter(adapter);

        navBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v;
                ImageView icon;
                TextView navText;
                int firstCompletelyVisiblePos = navBar.getFirstVisiblePosition();
                if(firstRun){
                    layout=(LinearLayout)navBar.getChildAt(firstCompletelyVisiblePos);
                    firstRun=false;
                }

                if (layout != null) {
                    v = layout.getChildAt(0);
                    icon = (ImageView) layout.getChildAt(1);
                    navText = (TextView) layout.getChildAt(2);
                    v.setBackgroundColor(Color.WHITE);
                    icon.setImageResource(imageId[privPosition]);
                    navText.setTextColor(Color.GRAY);
                }
                privPosition = position;
                layout = (LinearLayout) view;
                v = layout.getChildAt(0);
                icon = (ImageView) layout.getChildAt(1);
                navText = (TextView) layout.getChildAt(2);
                v.setBackgroundColor(Color.argb(255,3, 169,244));
                icon.setImageResource(pink[position]);
                navText.setTextColor(Color.argb(255, 3,169, 244));
                supportInvalidateOptionsMenu();
                pos=position;
                switch (position) {
                    case 0:
                        PostFragment frag=PostFragment.newInstance();
                        displayFragment(frag);
                        break;
                    case 1:
                        TimeTableFragment timeTableFragment=TimeTableFragment.getInstance();
                        displayFragment(timeTableFragment);
                        break;
                    case 2:
                        ProfileFragment profileFragment=new ProfileFragment();
                        displayFragment(profileFragment);
                        break;
                    case 3:
                        ConstitutionFragment constitutionFragment=ConstitutionFragment.getInstance();
                        displayFragment(constitutionFragment);
                        break;
                    case 4:
                        GypeeFragment gypeeFragment=GypeeFragment.getInstance();
                        displayFragment(gypeeFragment);
                        break;
                    case 5:
                        CourseListFragment courseListFragment=CourseListFragment.getInstance();
                        displayFragment(courseListFragment);
                        break;
                    case 6:
                        FeedbackFragment feedbackFragment=FeedbackFragment.newInstance();
                        displayFragment(feedbackFragment);
                        break;
                    case 7:
                        HelpFragment helpFragment=HelpFragment.newInstance();
                        displayFragment(helpFragment);
                        break;

                }
            }
        });
        TextView team= findViewById(R.id.team);
        team.setTypeface(roboto);


    }
    public void displayFragment(Fragment frag){
        drawerLayout.closeDrawers();
        fm.beginTransaction().replace(R.id.main_container,frag).commit();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.POSITION,pos);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
