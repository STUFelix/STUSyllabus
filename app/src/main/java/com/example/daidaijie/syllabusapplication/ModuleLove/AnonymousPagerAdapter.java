package com.example.daidaijie.syllabusapplication.ModuleLove;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.daidaijie.syllabusapplication.ModuleLove.circle.mainmenu.AnonymousFragment;

/**
 * Created by daidaijie on 2016/7/17.
 */

public class AnonymousPagerAdapter extends FragmentStatePagerAdapter {
    public AnonymousPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private static final String TAG = "CreateFragmentTest";
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: "+position);
        return new AnonymousFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
