package com.example.daidaijie.syllabusapplication.recommendation.searchList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.recommendation.bean.CourseBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.TeacherBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.finalResultBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class SearchListActivity extends BaseActivity implements SearchListContract.view{
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.recomClass_recycleview)
    RecyclerView mRecycleView;
    @BindView(R.id.recomClass_tips)
    TextView mTips;

    SearchItemAdapter mAdapter;
    @Inject
    SearchListPrsenter mPresenter;
    Object bean;
    private int mode;

    private static final String TAG = "SearchListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolBar);
        mTips.setVisibility(View.GONE);
        mAdapter = new SearchItemAdapter(new ArrayList<finalResultBean>());
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new SearchItemAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//            }
//        });
        EventBus.getDefault().register(this);
        DaggerSearchListComponnent.builder()
                .appComponent(mAppComponent)
                .searchListModule(new SearchListModule(this))
                .build().inject(this);

        Intent intent = getIntent();
        mode = intent.getIntExtra("MODE",0);
        mPresenter.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mode==1){
            mPresenter.showFinalResultByCourse((CourseBean)bean);
        }else if(mode ==2){
            mPresenter.showFinalResultByTeacher((TeacherBean)bean);
        }
    }

    @Override
    public void showTips(String Msg) {
        mRecycleView.setVisibility(View.GONE);
        mTips.setVisibility(View.VISIBLE);
        mTips.setText(Msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(Object bean){
        this.bean = bean;
    }

    @Override
    public void showMsg(String Msg) {
        Toast.makeText(this,Msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showList(List<finalResultBean> Bean) {
        mAdapter.updateData(Bean);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recom_list;
    }

    @Override
    public void closePage() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
