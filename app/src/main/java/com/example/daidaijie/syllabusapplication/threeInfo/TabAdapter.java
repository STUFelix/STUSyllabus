package com.example.daidaijie.syllabusapplication.threeInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.daidaijie.syllabusapplication.threeInfo.main.InfoMainActivity;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/21.
 */

public class TabAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public TabAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //设置tablayout的标题
    @Override
    public CharSequence getPageTitle(int position) {
        switch (InfoMainActivity.BLOCK){
            case 1:
                return InfoMainActivity.InfoTabTitle[position];
            case 2:
                return InfoMainActivity.FindTabTitle[position];
            case 3:
                return InfoMainActivity.LoveTabTitle[position];
        }
        return null;
    }
}
