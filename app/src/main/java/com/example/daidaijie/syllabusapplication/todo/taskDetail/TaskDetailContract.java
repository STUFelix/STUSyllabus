package com.example.daidaijie.syllabusapplication.todo.taskDetail;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;

/**
 * Created by 16zhchen on 2018/9/15.
 */

public class TaskDetailContract {
    interface Presenter extends BasePresenter {
        void deleteTask();
    }
    interface View extends BaseView<Presenter> {
        void showTaskDetail(TaskBean task);
        void editTask();
        void closePage();
        void showMsg(String msg);
    }
}
