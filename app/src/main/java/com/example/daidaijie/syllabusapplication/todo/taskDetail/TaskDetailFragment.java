package com.example.daidaijie.syllabusapplication.todo.taskDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseFragment;
import com.example.daidaijie.syllabusapplication.todo.addOrEditTask.TaskAEActivity;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;
import com.example.daidaijie.syllabusapplication.todo.mainMenu.TaskListPresenter;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by 16zhchen on 2018/9/15.
 */

public class TaskDetailFragment extends BaseFragment implements TaskDetailContract.View{
    private long TASK_ID;
    @BindView(R.id.detail_title)
    TextView mTitle;
    @BindView(R.id.detail_content)
    TextView mContent;
    @BindView(R.id.detail_status)
    TextView mStatus;
    @BindView(R.id.detail_alarm_state)
    TextView mAlarmState;

    @Inject
    TaskDetailPresenter mTaskDetailPresenter;

    public static TaskDetailFragment newInstance(long taskID){
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setTASK_ID(taskID);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.detail_menu_edit:{
                editTask();
                return true;
            }
            case R.id.detail_menu_delete:{
                mTaskDetailPresenter.deleteTask();
                return true;
            }
        }
        return false;
    }

    @Override
    public void showTaskDetail(TaskBean task) {
        mTitle.setText(task.getTitle().toString());
        mContent.setText(task.getContext());
        String str ="";
        if(task.getStatus()==0)
            str = "进行中";
        else if(task.getStatus()==1)
            str = "已完成";
        mStatus.setText(str);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(task.getIsAlarm()){
            mAlarmState.setText("提醒时间:"+sdf.format(task.getTime()));
        }else{
            mAlarmState.setText("无提醒");
        }
    }

    @Override
    public void editTask() {
        Intent intent = new Intent(getContext(), TaskAEActivity.class);
        intent.putExtra("TYPE",TASK_ID);
        startActivity(intent);

    }

    @Override
    public void closePage() {
        getActivity().finish();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        DaggerTaskDetailComponent.builder()
                .taskDetailModule(new TaskDetailModule(this))
                .userComponent(UserComponent.buildInstance(mAppComponent))
                .build().inject(this);
        mTaskDetailPresenter.setTASK_ID(TASK_ID);


    }

    @Override
    public void onResume() {
        super.onResume();
        mTaskDetailPresenter.start();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_task_detail;
    }

    public void setTASK_ID(long TASK_ID) {
        this.TASK_ID = TASK_ID;
    }
}
