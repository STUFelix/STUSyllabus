package com.example.daidaijie.syllabusapplication.Modulefindlost.personal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.daidaijie.syllabusapplication.Modulefindlost.personal.comments.CommnetsFragment;

/**
 * Created by daidaijie on 2016/7/17.
 */

public class PersonalAdapter extends FragmentStatePagerAdapter {
    public PersonalAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CommnetsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
