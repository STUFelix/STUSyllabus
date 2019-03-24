package com.example.daidaijie.syllabusapplication.mystu;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.mystu.request.CourseDiscussionRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CourseInfoActivity extends BaseActivity {

    @BindView(R.id.rv_testlist)
    RecyclerView mRvTestList;
    @BindView(R.id.courseinfo_up_pageno)
    FloatingActionButton floatingActionButton_up;
    @BindView(R.id.courseinfo_down_pageno)
    FloatingActionButton floatingActionButton_down;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static int pageNoTemp =1;
    public static int pageNo = 1;
    /**请求的页码，固定每页10个item*/
    private final int pageSize = 10;
    private List<Map<String,String>> mClist =new ArrayList<Map<String, String>>();
    private List<Map<String,String>> mMlist =new ArrayList<Map<String, String>>();
    private Boolean flag=true;

    private String cookies;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message message){
            if (message.what==20001){

                mClist = CourseDiscussionRequest.getClist();
                mMlist = CourseDiscussionRequest.getMlist();

                if(mClist.size()<10){
                    Toast.makeText(CourseInfoActivity.this,"已无更多信息\n存疑请到官网确认",Toast.LENGTH_SHORT).show();
                    flag=false;
                    pageNo--;
                    pageNoTemp--;
                }else {
                    /**最主要的函数*/
                    toRequestAdapter();
                    flag=true;
                }
                setListening();
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        toRequest();
    }

    @Override
    protected int getContentView() {
        return R.layout.mystu_courseinfolist;
    }
    /**防止Handle内存泄漏*/
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
    private void toRequestAdapter(){
        mRvTestList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvTestList.setAdapter(new CourseInfoListAdapter(this));
    }
    private void init(){

        App app =(App)getApplication();
        cookies = app.getTCookie();
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        setupTitleBar(mToolbar);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                toRequest();
            }
        });
    }

    private void toRequest() {
        swipeRefreshLayout.setRefreshing(true);
        Log.i("CourseInfoActivity","toRequest"+pageNo+"#Temp:"+pageNoTemp);
        CourseDiscussionRequest courseDiscussion =
                new CourseDiscussionRequest
                        (cookies,pageSize,pageNo,mHandler,CourseInfoActivity.this,swipeRefreshLayout,pageNoTemp);
        courseDiscussion.getCourseDiscussion();
    }



    private void setListening(){
        floatingActionButton_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CourseDiscussionRequest.isFastDoubleClick()){

                    if(pageNo >= 2){
                        flag=false;
                        if(mClist.size()!=0)mClist.clear();
                        if(mMlist.size()!=0)mMlist.clear();
                        pageNo--;
                        Log.i("CourseInfoActivity","setListeningup"+pageNo);
                        toRequest();
                    }
                 }else {
                    Toast.makeText(CourseInfoActivity.this,"请勿过快点击",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(flag){
            floatingActionButton_down.setEnabled(true);
            floatingActionButton_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CourseDiscussionRequest.isFastDoubleClick()){
                        if(mClist.size()!=0)mClist.clear();
                        if(mMlist.size()!=0)mMlist.clear();
                        pageNo++;
                        Log.i("CourseInfoActivity","setListeningdown"+pageNo);
                        toRequest();
                    }else {
                        Toast.makeText(CourseInfoActivity.this,"请勿过快点击",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            floatingActionButton_down.setEnabled(false);
        }
    }


}
