package com.example.daidaijie.syllabusapplication.todo.mainMenu;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.adapter.TaskItemAdapter;
import com.example.daidaijie.syllabusapplication.base.BaseFragment;
import com.example.daidaijie.syllabusapplication.todo.addOrEditTask.TaskAEActivity;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;
import com.example.daidaijie.syllabusapplication.todo.taskDetail.TaskDetailActivity;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class TaskListFragment extends BaseFragment implements TaskListContract.view{

    @BindView(R.id.tasklist_RecyclerView)
    RecyclerView mTaskListRecycleView;
    @BindView(R.id.refreshTaskLayout)
    SwipeRefreshLayout mRefreshTaskLayout;


    boolean isNetDataChanged = false;

    @Inject
    TaskListPresenter mTaskListPresenter;
    //int Position;
    private TaskItemAdapter mTaskItemAdapter;
    private static final String TAG = "TaskListFragment";
    public static TaskListFragment newInstance(){
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.d(TAG, "init:refreshtest ");
        mTaskItemAdapter = new TaskItemAdapter(new ArrayList<TaskBean>());

        mTaskListRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTaskListRecycleView.setAdapter(mTaskItemAdapter);
        mTaskItemAdapter.setOnItemClickListener(new TaskItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击子项后跳转
                switch (view.getId()){
                    case R.id.item_status:{
                        //Toast.makeText(getContext(),"check",Toast.LENGTH_SHORT).show();
                        AppCompatCheckBox status = (AppCompatCheckBox)view;
                        if(status.isChecked()){
                            mTaskListPresenter.updateTask(mTaskItemAdapter.getTask(position).getId(),1);
                        }else{
                            mTaskListPresenter.updateTask(mTaskItemAdapter.getTask(position).getId(),0);
                        }
                        mTaskItemAdapter.notifyDataSetChanged();
                        break;
                    }
                    default:{
                        if(mTaskItemAdapter.getTask(position).getId()!=0){
                            Intent intent = new Intent(getContext(), TaskDetailActivity.class);
                            intent.putExtra("TYPE",mTaskItemAdapter.getTask(position).getId());
                            startActivity(intent); }
                    }
                }

            }
        });
        mTaskItemAdapter.setOnItemLongClickListener(new TaskItemAdapter.OnItemLongClickListener(){
            @Override
            public void onItemLongClick(final View view, final int position) {
                final String items[] ={"编辑","删除"};
                AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle(mTaskItemAdapter.getTask(position).getTitle())
                        .setPositiveButton("编辑", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(getContext(), TaskAEActivity.class);
                                intent.putExtra("TYPE",mTaskItemAdapter.getTask(position).getId());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog dia = new AlertDialog.Builder(view.getContext())
                                        .setMessage("是否删除 "+mTaskItemAdapter.getTask(position).getTitle())
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                mTaskListPresenter.DeteleTask(mTaskItemAdapter.getTask(position).getId());
                                                dialogInterface.dismiss();
                                                Toast.makeText(view.getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                                mTaskItemAdapter.updateData(mTaskListPresenter.getList());
                                                mTaskItemAdapter.notifyDataSetChanged();
                                            }
                                        }).create();
                                dia.show();
                            }
                        })
                        .create();
                dialog.show();
            }
        });


        DaggerTaskListComponent.builder()
                .userComponent(UserComponent.buildInstance(mAppComponent))
                .taskListModule(new TaskListModule(this))
                .build().inject(this);

        final SwipeRefreshLayout mContentView = (SwipeRefreshLayout) getActivity().findViewById(R.id.list_contextFrame);
        mContentView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
                mContentView.setRefreshing(false);
                mTaskListPresenter.loadData();
            }
        });

    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();

        if(isNetDataChanged){
            mTaskListPresenter.loadData();
            isNetDataChanged = false;
        }else{
            mTaskListPresenter.start();
        }


    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_task_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }




    @Override
    public void showFailMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showList(List<TaskBean> taskBeen) {
        mTaskItemAdapter.updateData(taskBeen);
    }

    @Override
    public boolean getStatus() {
        return isNetDataChanged;
    }

    @Override
    public void setStatus(boolean s) {
        isNetDataChanged = s;
    }
}
