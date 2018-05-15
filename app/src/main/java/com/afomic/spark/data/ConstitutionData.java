package com.afomic.spark.data;

import android.text.Html;

import com.afomic.spark.model.Constitution;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by afomic on 18-Oct-16.
 */
public class ConstitutionData {
    private ArrayList<Constitution> mConstitutions;
    private static ConstitutionData data;

    public static ConstitutionData get() {
        if (data == null) {
            data = new ConstitutionData();
        }
        return data;
    }

    private ConstitutionData() {
        mConstitutions = new ArrayList<>();
        addData();
    }

    public void addData() {
        FirebaseDatabase.getInstance().getReference("constitution/nacoss")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Constitution constitution = snapshot.getValue(Constitution.class);
                            mConstitutions.add(constitution);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    public ArrayList<Constitution> getConstitution(int article) {
        ArrayList<Constitution> articleConstitution = new ArrayList<>();
        boolean hit = false;
        for (Constitution constitution : mConstitutions) {
            if (constitution.getArticle() == article) {
                articleConstitution.add(constitution);
                if (!hit) {// since all the data are sequential once we are done with all the article stop checking
                    hit = true;
                }

            } else if (hit) {
                return articleConstitution;
            }
        }

        return articleConstitution;

    }

    /*
     *this method return the arrayList of all the constitution which contain the searched keyword
     */
    public ArrayList<Constitution> search(String query) {
        ArrayList<Constitution> array = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ArrayList<Constitution> article = getConstitution(i);
            for (Constitution section : article) {
                String sectionText = section.getContent().toLowerCase();
                sectionText = getString(sectionText);
                String sectionName = section.getTitle().toLowerCase();
                query = query.toLowerCase();
                if (sectionText.contains(query) || sectionName.contains(query)) {
                    array.add(section);
                }
            }
        }
        return array;
    }

    public String getString(String content) {
        return Html.fromHtml(content).toString();
    }

    public int size() {
        return 7;
    }

    public Constitution getSection(int article, int section) {
        ArrayList<Constitution> arrayList = getConstitution(article);
        for (Constitution entry : arrayList) {
            if (entry.getSection() == section) {
                return entry;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return mConstitutions.isEmpty();
    }
}
