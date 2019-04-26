package com.example.daidaijie.syllabusapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.daidaijie.syllabusapplication.todo.mainMenu.TaskListFragment;

public class TaskPagerAdapter extends FragmentStatePagerAdapter {

    public TaskPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {

        return TaskListFragment.newInstance();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
