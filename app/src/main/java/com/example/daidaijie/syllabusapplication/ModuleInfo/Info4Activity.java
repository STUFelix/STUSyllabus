package com.example.daidaijie.syllabusapplication.ModuleInfo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;

import butterknife.BindView;

public class Info4Activity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.containerViewPager)
    ViewPager mContainerViewPager;
    StuCirclePagerAdapter mPagerAdapter;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    private long touchSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar.setTitle("");
        setupToolbar(mToolbar);
        setSupportActionBar(mToolbar);

        touchSecond = 0;
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (SystemClock.currentThreadTimeMillis() - touchSecond < 200) {
                    EventBus.getDefault().post(new ToTopEvent(false, false));
                } else {
                    Toast.makeText(STUCircleActivity.this, "再次点击回到顶部", Toast.LENGTH_SHORT).show();
                }
                touchSecond = SystemClock.currentThreadTimeMillis();*/

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPagerAdapter = new StuCirclePagerAdapter(getSupportFragmentManager());

        mContainerViewPager.setAdapter(mPagerAdapter);

        //      设置tabLayout关联containerViewPager
        mContainerViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mContainerViewPager);
//      修改两个Tab的文字
        mTabLayout.getTabAt(0).setText("生活");
        mTabLayout.getTabAt(1).setText("兼职");
        mTabLayout.getTabAt(2).setText("学习");
        mTabLayout.getTabAt(3).setText("研究");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_stucircle;
    }


}
