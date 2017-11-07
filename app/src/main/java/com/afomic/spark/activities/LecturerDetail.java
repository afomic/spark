package com.afomic.spark.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.afomic.spark.R;
import com.afomic.spark.data.Constants;
import com.afomic.spark.model.Profile;
import com.greenfrvr.hashtagview.HashtagView;

import java.util.Arrays;
import java.util.List;

public class LecturerDetail extends AppCompatActivity {

    ImageView picture;
    TextView degree,email,roomNumber,phone;
    HashtagView areaOfSpecialization;
    CollapsingToolbarLayout collapsingToolbar;
    Profile lecturerProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_detail);
         Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //adding the name of exco_detail as title of the collapsing toolbar
        collapsingToolbar=(CollapsingToolbarLayout) findViewById(R.id.lecturer_collapsing_toolbar);
        collapsingToolbar.setExpandedTitleGravity(Gravity.BOTTOM);


        //get the position of the exco pressed;
       lecturerProfile=getIntent().getParcelableExtra(Constants.EXTRA_PROFILE);

        //getting referencing to all the view use
        degree=(TextView) findViewById(R.id.lecturer_deg);
        phone=(TextView) findViewById(R.id.lecturer_phone);
        email=(TextView) findViewById(R.id.lecturer_email);
        areaOfSpecialization=(HashtagView) findViewById(R.id.tags);
        roomNumber=(TextView) findViewById(R.id.lecturer_room);
        TextView level=(TextView) findViewById(R.id.lecturer_level);
        picture=(ImageView) findViewById(R.id.lecturer_image);
        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setRippleColor(Color.YELLOW);
        //Todo use glide to load image into the lecturer Image
//        if(bit==null){
//            picture.setImageResource(R.drawable.user);
//        }else {
//            picture.setImageBitmap(bit);
//        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + lecturerProfile.getTelephoneNumber()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });



        //setting the value of the views on exco detail page
        level.setText(lecturerProfile.getLevel());
        collapsingToolbar.setTitle(lecturerProfile.getName());
        degree.setText(lecturerProfile.getDegrees());
        email.setText(lecturerProfile.getEmail());
        List<String> tags=processString(lecturerProfile.getAreaOfSpecialization());
        areaOfSpecialization.setData(tags);
        phone.setText(lecturerProfile.getTelephoneNumber());
        roomNumber.setText(lecturerProfile.getRoomNumber());
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+lecturerProfile.getEmail())); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Good day sir,");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


    }
    private List processString(String text){
        String[] tags=text.split(", ");
        return  Arrays.asList(tags);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
