package com.example.daidaijie.syllabusapplication.Modulefindlost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.mainmenu.FindLostFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daidaijie on 2016/7/17.
 */

public class FindLostPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> TabFragement;
    public FindLostPagerAdapter(FragmentManager fm) {
        super(fm);
        TabFragement = new ArrayList<>();
        for(int i=0;i<2;i++){
            Fragment frag = new FindLostFragment();
            Bundle args = new Bundle();
            args.putInt("position",i);
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
        return 2;
    }
}
