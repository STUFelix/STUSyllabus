package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.daidaijie.syllabusapplication.threeInfo.info.InfoFragment;

/**
 * Created by 16zhchen on 2018/12/2.
 */

public class InfoPageAdapter extends FragmentStatePagerAdapter{
    public InfoPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //载入对应Fragment
        //毕竟是同一个模块直接载入相同的布局就好
        //position对应标签页
        if(position == 0){
            return Info4Fragment.newInstance();
        }else{
            return new InfoFragment();
        }
    }

    @Override
    public int getCount() {
        //返回总页面数
        return 4;
    }
}
