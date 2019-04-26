package com.example.daidaijie.syllabusapplication.todo.addOrEditTask;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TodoDataManager;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;
import com.example.daidaijie.syllabusapplication.todo.ITaskModel;
import com.example.daidaijie.syllabusapplication.todo.mainMenu.TaskListActivity;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by 16zhchen on 2018/9/16.
 */

public class TaskAEPresenter implements TaskAEContract.Presenter {
    TodoDataManager dataManager;
    private TaskAEContract.View mView;
    private final int defaultState = 0;
    private long taskType = 0;

    private IUserModel mIUserModel;
    private ITaskModel mTaskModel;
    private static final String TAG = "TaskAEPresenter";

    @Inject
    @PerFragment
    public TaskAEPresenter(ITaskModel iTaskModel,TaskAEContract.View view){
        dataManager = TodoDataManager.getInstance();
        mView = view;
        mTaskModel  = iTaskModel;
        mIUserModel = mTaskModel.getmIUserModel();
    }

    @Override
    public void saveTaskForNew(String title, String content, boolean isAlarm, Date alarmTime) {

        TaskBean task = new TaskBean(
                null,0,title,content,defaultState,isAlarm,alarmTime
        );

        long temp = dataManager.addTasks(task);
        Log.d(TAG, "saveTaskForNew: DataManager:"+String.valueOf(temp));
        mView.showMsg("新建成功");
        mTaskModel.addNewTask(title,content)
                .subscribe(new Subscriber<HttpBean>() {
                    @Override
                    public void onCompleted() {
                        mView.closePage();
                        //读取返回的id，更新数据库
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMsg(e);
                    }

                    @Override
                    public void onNext(HttpBean httpBean) {
                        Log.d(TAG, "onNext: "+httpBean.getStatus());
                    }
                });

    }

    @Override
    public void saveTaskForEdit(String title, String content, int state, boolean isAlarm, Date alarmTime) {
        int taskID = dataManager.getTaskById(taskType).getServerID();
        TaskBean task = new TaskBean(
                taskType,taskID,title,content,state,isAlarm,alarmTime
        );
        dataManager.updateTasks(task);
        mView.showMsg("修改成功");
        mTaskModel.editTask(task.getServerID(),task.getTitle(),task.getContext())
                .subscribe(new Subscriber<HttpBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMsg(e);
                    }

                    @Override
                    public void onNext(HttpBean httpBean) {
                        Log.d(TAG, "onNext: "+httpBean.getStatus());
                    }
                });
        mTaskModel.updateStatus(task.getServerID(),task.getStatus())
                .subscribe(new Subscriber<HttpBean>() {
                    @Override
                    public void onCompleted() {
                        mView.closePage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMsg(e);
                    }

                    @Override
                    public void onNext(HttpBean httpBean) {
                        Log.d(TAG, "onNext: "+httpBean.getStatus());
                    }
                });

    }

    @Override
    public TaskBean findTask(Long id) {
        return dataManager.getTaskById(id);
    }

    @Override
    public void start() {
        //编辑状态则导入信息
        if(taskType != TaskListActivity.ADD)
            mView.setTask(new Long(taskType));
    }



    @Override
    public void setTaskType(Long taskType) {
        this.taskType = taskType;
    }

    @Override
    public long getTaskNum() {
        return dataManager.getAllTasks().size();
    }


    private void showErrorMsg(Throwable e){
        if(e instanceof HttpException){
            ResponseBody body =  ((HttpException) e).response().errorBody();
            try{
                mView.showMsg(body.string());
            }catch (IOException IOe) {
                IOe.printStackTrace();
            }
        }
    }
}
