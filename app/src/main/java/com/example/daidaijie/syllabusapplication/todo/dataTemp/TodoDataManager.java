package com.example.daidaijie.syllabusapplication.todo.dataTemp;


import android.util.Log;

import com.example.daidaijie.syllabusapplication.App;

import java.util.ArrayList;
import java.util.List;

public class TodoDataManager {
    private static TodoDataManager INSTANCE = null;
    private TaskBeanDao taskDao;
    private List<TaskBean> tasksList;
    private App base;
    private static final String TAG = "TaskListFragment";

    private TodoDataManager(){
        Log.d(TAG, "DataManager: ");
        base = App.getInstances();
        taskDao = base.getDaoSession().getTaskBeanDao();
        tasksList = getAllTasks();
    }

    public static TodoDataManager getInstance(){
        if(INSTANCE == null){
            Log.d(TAG, "getInstance: old");
            INSTANCE  = new TodoDataManager();
        }
        Log.d(TAG, "getInstance: new");
        return INSTANCE;
    }

    public List<TaskBean> getAllTasks() {
        List<TaskBean> taskList = taskDao.queryBuilder()
                .where(TaskBeanDao.Properties.Id.notEq(999))
                .limit(99).build().list();
        return taskList;
    }

    public List<String> getAllTasksTitle(){
        List<String> title = new ArrayList<>();
        List<TaskBean> taskList = getAllTasks();
        for(TaskBean t:taskList){
            title.add(t.getTitle());
        }
        return title;
    }

    public TaskBean getTaskById(long id) {
        TaskBean task = taskDao.queryBuilder()
                .where(TaskBeanDao.Properties.Id.eq(id))
                .unique();
        String title = task.getTitle();
        return task;
    }


    public long addTasks(TaskBean task) {
        Log.d(TAG, "addTasks: "+task.getTitle());
        return taskDao.insert(task);
    }

    public int deleteTaskById(Long id){
        if(id != null){
            taskDao.deleteByKey(id);
            return 1;
        }
        return 0;
    }

    public int updateTasks(TaskBean task) {
        String title = task.getTitle();
        long id = task.getId().longValue();
        TaskBean dbTask = taskDao.queryBuilder()
                .where(TaskBeanDao.Properties.Id.eq(task.getId()))
                .unique();
        Log.d(TAG, "updateTasks: "+dbTask.getId()+" "+dbTask.getTitle()+" "+ dbTask.getIsAlarm());
        if(dbTask != null){
            dbTask.setTitle(task.getTitle());
            dbTask.setContext(task.getContext());
            dbTask.setStatus(task.getStatus());
            dbTask.setIsAlarm(task.getIsAlarm());
            dbTask.setTime(task.getTime());
            taskDao.update(dbTask);
            return 1;
        }
        return 0;
    }

    public void deleteAll(){
        taskDao.deleteAll();
    }

    public void insertAll(List<TaskBean> list){
        taskDao.insertInTx(list);
    }
}
