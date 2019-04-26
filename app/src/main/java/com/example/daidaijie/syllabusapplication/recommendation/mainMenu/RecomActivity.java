package com.example.daidaijie.syllabusapplication.recommendation.mainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.recommendation.bean.BaseAdapterBean;
import com.example.daidaijie.syllabusapplication.recommendation.searchList.SearchListActivity;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;



public class RecomActivity extends BaseActivity implements RecomContract.view {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.recom_search)
    SearchView mSearchView;
    @BindView(R.id.recom_recycleview)
    RecyclerView mRecycleView;
    @BindView(R.id.recom_search_type)
    Spinner searchType;
    private static final String TAG = "RecomActivity";

    RecomItemAdapter mAdapter;
    private ArrayAdapter<String> adapter;
    @Inject
    RecomPresenter mPresenter;
    boolean isM3FirstPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolBar);

        BaseAdapterBean<String> base = new BaseAdapterBean();
        base.setType(RecomItemAdapter.Q1M3);
        base.setData(new ArrayList<String>());

        mAdapter = new RecomItemAdapter(base);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecomItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int mode = mPresenter.getMode();
                if(mode==1 || mode ==2){
                    EventBus.getDefault().postSticky(mAdapter.getmData().getData().get(position));
                    Intent intent = new Intent(RecomActivity.this, SearchListActivity.class);
                    intent.putExtra("MODE",mPresenter.getMode());
                    startActivity(intent);
                }else if(mode == 3){
                    if(isM3FirstPage){
                        mPresenter.getAllCourseBydepartByQ2M3((String)mAdapter.getmData().getData().get(position));
                        isM3FirstPage = false;
                    }else{
                        EventBus.getDefault().postSticky(mAdapter.getmData().getData().get(position));
                        Intent intent = new Intent(RecomActivity.this, SearchListActivity.class);
                        intent.putExtra("MODE",1);
                        startActivity(intent);
                    }
                }

            }
        });

        //处理类型选择
        createSpinnerAdapter();
        searchType.setAdapter(adapter);

        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("请选择模式");
        searchType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPresenter.setMode(i+1);
                mSearchView.setSubmitButtonEnabled(true);
                switch (i){
                    case 0:
                        mSearchView.setQueryHint("请输入课程名称");
                        break;
                    case 1:
                        mSearchView.setQueryHint("请输入教师名称");
                        break;
                    case 2:
                        mSearchView.setQueryHint("请输入开课单位");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSearchView.clearFocus();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { 
            @Override 
            public boolean onQueryTextSubmit(String s) {

                switch (searchType.getSelectedItemPosition()){
                    case RecomItemAdapter.Q1M1:
                        mPresenter.getAllCourseByQ1M1(s);
                        break;
                    case RecomItemAdapter.Q1M2:
                        mPresenter.getAllTeacherByQ1M2(s);
                        break;
                    case RecomItemAdapter.Q1M3:{
                        isM3FirstPage = true;
                        mPresenter.getAllUnitByQ1M3(s);
                        break;
                    }
                }
                return false; 
            } 
            @Override 
            public boolean onQueryTextChange(String s) { 
                Log.e(TAG, "TextChange --> " + s); 
                return false; } 
        });


        DaggerRecomComponnent.builder()
                .appComponent(mAppComponent)
                .recomModule(new RecomModule(this))
                .build().inject(this);
        //读取所有单位并显示
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchView.clearFocus();
        mSearchView.setFocusable(false);
    }

    @Override
    public void showList(BaseAdapterBean Bean) {
        mAdapter.updateData(Bean);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recom;
    }
    private void createSpinnerAdapter(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("按课程名称");
        list.add("按老师名字");
        list.add("按开课单位");
        adapter = new ArrayAdapter<String>(this,R.layout.item_status_spinner,list);
        adapter.setDropDownViewResource(R.layout.item_status_spinner_drop);

    }

    @Override
    public void showMsg(String Msg) {
        Toast.makeText(this,Msg,Toast.LENGTH_SHORT).show();
    }

}
