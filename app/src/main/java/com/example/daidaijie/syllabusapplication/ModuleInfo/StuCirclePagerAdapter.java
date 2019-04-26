package com.example.daidaijie.syllabusapplication.ModuleInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.daidaijie.syllabusapplication.ModuleInfo.circle.mainmenu.StuCircleFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daidaijie on 2016/7/17.
 */

public class StuCirclePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> TabFragement;
    public StuCirclePagerAdapter(FragmentManager fm) {
        super(fm);
        TabFragement = new ArrayList<>();
        for(int i=0;i<4;i++){
            Fragment frag = new StuCircleFragment();
            Bundle args = new Bundle();
            args.putInt("position",i+1);
            frag.setArguments(args);
            TabFragement.add(frag);
        }
    }

    private static final String TAG = "CreateFragmentTest";
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: "+position);
        return TabFragement.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
