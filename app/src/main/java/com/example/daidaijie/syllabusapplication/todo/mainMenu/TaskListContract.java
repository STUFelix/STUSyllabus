package com.example.daidaijie.syllabusapplication.todo.mainMenu;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;

import java.util.List;


public interface TaskListContract {
    interface presenter extends BasePresenter{
        void loadData();
        List<TaskBean> getList();
        void DeteleTask(long TaskId);
        void DeteleAllTask();
        void updateTask(long TaskId,int status);
    }
    interface view extends BaseView<presenter>{
        //void showRefresh(boolean isShow);
        void showFailMessage(String msg);
        void showList(List<TaskBean> taskBeen);
        boolean getStatus();
        void setStatus(boolean s );
    }
}
