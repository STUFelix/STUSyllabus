package com.example.daidaijie.syllabusapplication.todo.taskDetail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaskDetailActivity extends BaseActivity {

    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_contextFrame)
    LinearLayout mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setupTitleBar(mToolbar);

        Intent intent = getIntent();
        long ID = intent.getLongExtra("TYPE",0);
        TaskDetailFragment taskDetailFragment =
                (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_contextFrame);
        if(taskDetailFragment == null){
            taskDetailFragment = TaskDetailFragment.newInstance(ID);
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(),taskDetailFragment,R.id.detail_contextFrame);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_task_detail;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
