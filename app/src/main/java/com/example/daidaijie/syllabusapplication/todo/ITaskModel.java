package com.example.daidaijie.syllabusapplication.todo;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;
import com.example.daidaijie.syllabusapplication.todo.bean.TODOAllBean;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBeanFromNet;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.util.List;

import rx.Observable;

/**
 * Created by 16zhchen on 2018/9/28.
 */

public interface ITaskModel {

    IUserModel getmIUserModel();

    Observable<TODOAllBean.DataBean.EvaListBean> getAllTaskFromNet();
    Observable<HttpBean> addNewTask(String title,String context);
    Observable<HttpBean> editTask(int todoID,String title,String context);
    Observable<HttpBean> updateStatus(int todoID,int status);
    Observable<HttpBean> deleteTask(int todoID);

}
