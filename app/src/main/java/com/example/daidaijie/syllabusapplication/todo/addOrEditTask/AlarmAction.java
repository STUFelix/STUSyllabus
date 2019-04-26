package com.example.daidaijie.syllabusapplication.todo.addOrEditTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TodoDataManager;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;

public class AlarmAction extends BroadcastReceiver {
    private static final String TAG = "TaskListFragment";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"定时任务提醒", Toast.LENGTH_SHORT).show();
        //显示通知栏
        NotificationCompat.Builder no=new NotificationCompat.Builder(context);
        //设置参数
        no.setDefaults(NotificationCompat.DEFAULT_ALL);
        TodoDataManager greenDaoManager = TodoDataManager.getInstance();
        long taskID = intent.getLongExtra("taskID",0);
        if(taskID == 0){
            no.setContentTitle("任务提醒");
            no.setContentText("您有待完成任务");
        }else{
            TaskBean task = greenDaoManager.getTaskById(taskID);
            no.setContentTitle("任务提醒");
            no.setContentText(task.getTitle());
            task.setIsAlarm(false);
            Log.d(TAG, "onReceive: "+task.getId()+" "+task.getTitle()+" "+task.getIsAlarm());
            int result = greenDaoManager.updateTasks(task);
            Log.d(TAG, "onReceive: "+Integer.toString(result));
        }
        no.setSmallIcon(R.mipmap.syllabus_icon);
        Notification notification=no.build();

        //通知管理器
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //发送通知
        notificationManager.notify(0x101,notification);
    }

}
