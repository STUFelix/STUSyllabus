package com.example.daidaijie.syllabusapplication.todo.addOrEditTask;

import android.content.Intent;
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


public class TaskAEActivity extends BaseActivity {

    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ae_contextFrame)
    LinearLayout mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setupTitleBar(mToolbar);

        TaskAEFragment taskAEFragment =
                (TaskAEFragment) getSupportFragmentManager().findFragmentById(R.id.ae_contextFrame);
        if(taskAEFragment == null){
            taskAEFragment = TaskAEFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    taskAEFragment,R.id.ae_contextFrame);
        }

        Intent intent = getIntent();
        long num = intent.getLongExtra("TYPE",0);
        taskAEFragment.setTaskType(num);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_task_ae;
    }
}
