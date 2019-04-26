package com.example.daidaijie.syllabusapplication.todo.mainMenu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.adapter.TaskPagerAdapter;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.bean.TaskAllBean;
import com.example.daidaijie.syllabusapplication.todo.addOrEditTask.TaskAEActivity;
import com.example.daidaijie.syllabusapplication.user.UserComponent;
import com.example.daidaijie.syllabusapplication.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaskListActivity extends BaseActivity {
    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
//    @BindView(R.id.contentViewPager)
//    ViewPager mContentViewPager;
    @BindView(R.id.tasklist_faddTask)
    FloatingActionButton mAddButton;
//    @BindView(R.id.list_contextFrame)
//    SwipeRefreshLayout mContentView;

    //TaskPagerAdapter mTaskPagerAdapter;
    TaskAllBean mTaskAllBean;
    public static final long ADD = 0;
    TaskListFragment taskListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_task_list);

        ButterKnife.bind(this);
        setupTitleBar(mToolbar);
//
        //添加fragment,可以移到init
        taskListFragment =
                (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.list_contextFrame);
        if(taskListFragment == null){
            taskListFragment = TaskListFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(),taskListFragment,R.id.list_contextFrame);
        }
        final TaskListFragment finalTaskListFragment = taskListFragment;
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask();
                Toast.makeText(view.getContext(),"添加任务",Toast.LENGTH_SHORT).show();
                taskListFragment.setStatus(true);
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_task_list;
    }

    public void addNewTask(){
        //跳转活动
        Intent intent = new Intent(this, TaskAEActivity.class);
        intent.putExtra("TYPE",ADD);
        startActivity(intent);
    }
}
