package com.example.daidaijie.syllabusapplication.todo.mainMenu;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.bean.UserInfo;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.todo.ITaskModel;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;
import com.example.daidaijie.syllabusapplication.todo.bean.TODOAllBean;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TodoDataManager;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by 16zhchen on 2018/9/14.
 */

public class TaskListPresenter implements TaskListContract.presenter {

    private static final String TAG = "TaskListFragment";



    private UserInfo mUserInfo;
    private TaskListContract.view mView;
    private ITaskModel mTaskModel;
    private IUserModel mIUserModel;
    private TodoDataManager dataManager;

    List<TaskBean> list;

    @Inject
    @PerFragment
    public TaskListPresenter(ITaskModel iTaskModel, TaskListContract.view view){
        mTaskModel  = iTaskModel;
        mIUserModel = mTaskModel.getmIUserModel();
        mView = view;
    }

    @Override
    public void loadData() {

        list = new ArrayList<>();
        mTaskModel.getAllTaskFromNet()
                .subscribe(new Subscriber<TODOAllBean.DataBean.EvaListBean>() {
                    @Override
                    public void onCompleted() {
                        DeteleAllTask();
                        dataManager.insertAll(list);
                        mView.showList(dataManager.getAllTasks());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showErrorMsg(e);
                    }

                    @Override
                    public void onNext(TODOAllBean.DataBean.EvaListBean evaListBean) {
                        int status = evaListBean.isStatus()?1:0;
                        TaskBean t = new TaskBean(null,evaListBean.getId(),
                                evaListBean.getTitle(),evaListBean.getContent(),
                                status,false ,null);
                        list.add(t);
                    }
                });
    }

    public void loadUserInfo() {
        mUserInfo = mIUserModel.getUserInfoNormal();
    }
    @Override
    public void start() {
        dataManager = TodoDataManager.getInstance();
        List<TaskBean> temp = getList();
        loadUserInfo();
        mView.showList(getList());
    }

    @Override
    public List<TaskBean> getList() {
        return dataManager.getAllTasks();
    }
    @Override
    public void DeteleTask(long TaskId){
        int taskID = dataManager.getTaskById(TaskId).getServerID();
        dataManager.deleteTaskById(TaskId);
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
    }

    @Override
    public void DeteleAllTask() {
        dataManager.deleteAll();
    }

    @Override
    public void updateTask(long TaskId, int status) {
        TaskBean  task = dataManager.getTaskById(TaskId);
        task.setStatus(status);
        dataManager.updateTasks(task);
        mTaskModel.updateStatus(task.getServerID(),status)
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
    }

    private void showErrorMsg(Throwable e){
        if(e instanceof HttpException){

            ResponseBody body =  ((HttpException) e).response().errorBody();
            int code  = ((HttpException) e).response().code();
            Log.d(TAG, "showErrorMsg: "+code);
            if(code == 401){
                mView.showFailMessage("请重新登陆");
                return;
            }
            Log.d(TAG, "showErrorMsg: "+code);
            Log.d(TAG, "showErrorMsg: "+body.toString());
            try{
                mView.showFailMessage(body.string());

            }catch (IOException IOe) {
                IOe.printStackTrace();
            }
        }
    }
}
