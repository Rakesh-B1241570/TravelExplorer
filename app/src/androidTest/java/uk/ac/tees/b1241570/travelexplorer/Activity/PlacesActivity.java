package uk.ac.tees.b1241570.travelexplorer.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import studio.knowhere.travelappg.R;
import studio.knowhere.travelappg.ui.Class.TopicsPagerAdapter;

public class PlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

      /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new TopicsPagerAdapter(getSupportFragmentManager(), this));

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }
}
