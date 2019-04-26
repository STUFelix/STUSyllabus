package com.example.daidaijie.syllabusapplication.ModuleInfo.personal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.daidaijie.syllabusapplication.ModuleInfo.personal.comments.CommnetsFragment;
import com.example.daidaijie.syllabusapplication.ModuleInfo.personal.posts.PostsFragment;

/**
 * Created by daidaijie on 2016/7/17.
 */

public class PersonalAdapter extends FragmentStatePagerAdapter {
    public PersonalAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PostsFragment.newInstance();
        } else {
            return CommnetsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
