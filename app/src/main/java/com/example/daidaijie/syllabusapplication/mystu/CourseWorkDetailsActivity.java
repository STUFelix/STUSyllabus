package com.example.daidaijie.syllabusapplication.mystu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class CourseWorkDetailsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.courseworkdetails_title)
    TextView tv_title;
    @BindView(R.id.courseworkdetails_start)
    TextView tv_start;
    @BindView(R.id.courseworkdetails_end)
    TextView tv_end;
    @BindView(R.id.courseworkdetails_submitstate)
    TextView tv_submitstate;
    @BindView(R.id.courseworkdetails_gradingstate)
    TextView tv_gradingstate;
    @BindView(R.id.courseworkdetails_content)
    TextView tv_content;
    private static Map<String, String> detailsMap  = new HashMap<String, String>();
    private int posiotion;


    @Override
    protected int getContentView() {
        return R.layout.mystu_courseworkdetails;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolbar);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        posiotion = intent.getIntExtra("position",0);


        detailsMap = CourseWorkUtil.getDetailsMap();
        tv_title.setText(CourseWorkUtil.getName(posiotion));
        tv_start.setText("开放时间：\t"+detailsMap.get("beginTime"));
        tv_end.setText("截止时间：\t"+detailsMap.get("endTime"));
        tv_submitstate.setText("提交状态：\t"+detailsMap.get("submitStatus"));
        tv_gradingstate.setText("评分状态：\t"+detailsMap.get("gradeStatus"));

        tv_content.setText(detailsMap.get("assign"));
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        Toast.makeText(CourseWorkDetailsActivity.this,"没有链接的数据\n若有链接请到官网确认",Toast.LENGTH_SHORT).show();

    }

}
