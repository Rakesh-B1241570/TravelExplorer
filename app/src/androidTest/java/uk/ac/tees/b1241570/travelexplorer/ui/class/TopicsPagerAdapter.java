package uk.ac.tees.b1241570.travelexplorer.ui;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import studio.knowhere.travelappg.R;

public class TopicsPagerAdapter extends FragmentPagerAdapter {

    private String[] mTopics;

    public TopicsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mTopics = context.getResources().getStringArray(R.array.topics);
    }

    @Override
    public Fragment getItem(int position) {
        return PlacesFragment.newInstance(mTopics[position]);
    }

    @Override
    public int getCount() {
        return mTopics.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTopics[position];
    }
}
