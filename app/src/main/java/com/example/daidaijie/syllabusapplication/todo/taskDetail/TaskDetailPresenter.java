package com.example.daidaijie.syllabusapplication.todo.taskDetail;


import android.util.Log;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TodoDataManager;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;
import com.example.daidaijie.syllabusapplication.todo.ITaskModel;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class TaskDetailPresenter implements TaskDetailContract.Presenter{
    private long mTaskID;
    private TodoDataManager dataManager;
    private TaskDetailContract.View mView;
    private ITaskModel mTaskModel;
    private IUserModel mIUserModel;
    private static final String TAG = "TaskDetailPresenter";

    @Inject
    @PerFragment
    public TaskDetailPresenter(ITaskModel iTaskModel,TaskDetailContract.View view){
        dataManager = TodoDataManager.getInstance();
        mTaskModel  = iTaskModel;
        mIUserModel = mTaskModel.getmIUserModel();
        mView = view;
    }

    @Override
    public void deleteTask() {
        int taskID = dataManager.getTaskById(mTaskID).getServerID();
        dataManager.deleteTaskById(mTaskID);
        mTaskModel.deleteTask(taskID)
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
        mView.showMsg("已删除");
        mView.closePage();
    }

    @Override
    public void start() {
        TaskBean task = dataManager.getTaskById(mTaskID);
        mView.showTaskDetail(task);
    }
    public void setTASK_ID(long TASK_ID) {
        mTaskID = TASK_ID;
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
