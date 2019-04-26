package com.example.daidaijie.syllabusapplication.todo;

import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;
import com.example.daidaijie.syllabusapplication.todo.bean.TODOAllBean;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 16zhchen on 2018/9/28.
 */

public class TaskModel implements ITaskModel {

    TodoApi mTodoApi;
    IUserModel mUserModel;

    public TaskModel(TodoApi todoApi, IUserModel iUserModel){

        mUserModel = iUserModel;
        mTodoApi = todoApi;
    }
    @Override
    public IUserModel getmIUserModel() {
        return mUserModel;
    }

    @Override
    public Observable<TODOAllBean.DataBean.EvaListBean> getAllTaskFromNet() {

        return mTodoApi.getAllTask(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),1,1,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<TODOAllBean, Observable<TODOAllBean.DataBean.EvaListBean>>() {
                    @Override
                    public Observable<TODOAllBean.DataBean.EvaListBean> call(TODOAllBean todoAllBean) {
                        return Observable.from(todoAllBean.getData().getEva_list());
                    }
                });
    }

    @Override
    public Observable<HttpBean> addNewTask(String title, String context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String start = sdf.format(calendar.getTime());
        calendar.add(Calendar.HOUR,1);
        String dead = sdf.format(calendar.getTime());
        return mTodoApi.addNewTask(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),"",title,context,start,dead,"",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpBean> editTask(int todoID, String title, String context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String start = sdf.format(calendar.getTime());
        calendar.add(Calendar.HOUR,1);
        String dead = sdf.format(calendar.getTime());
        return mTodoApi.editTask(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),todoID,"",title,context,start,dead,"",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpBean> updateStatus(int todoID, int status) {
        return mTodoApi.updateStatus(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),todoID,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpBean> deleteTask(int todoID) {
        return mTodoApi.deleteTask(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),todoID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
