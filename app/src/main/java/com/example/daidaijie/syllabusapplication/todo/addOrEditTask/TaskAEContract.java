package com.example.daidaijie.syllabusapplication.todo.addOrEditTask;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;

import java.util.Date;

/**
 * Created by 16zhchen on 2018/9/16.
 */

public class TaskAEContract {
    interface Presenter extends BasePresenter {
        void saveTaskForNew(String title, String content, boolean isAlarm, Date alarmTime);
        void saveTaskForEdit(String title,String content,int state,boolean isAlarm,Date alarmTime);
        TaskBean findTask(Long id);
        void setTaskType(Long taskType);
        long getTaskNum();
    }
    interface View extends BaseView<Presenter> {
        void showMsg(String msg);
        void setTask(Long id);
        void setTitle(String title);
        void setContent(String content);
        void closePage();

    }
}
