package com.example.daidaijie.syllabusapplication.todo.dataTemp;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by 16zhchen on 2018/9/15.
 */
@Entity
public class TaskBean {
    @Id
    private Long id;

    @NotNull
    private int serverID;
    private String title;
    private String context;
    private int status;
    private boolean isAlarm;
    private Date time;//提醒时间

    public Date getTime() {
        return this.time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getContext() {
        return this.context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getIsAlarm() {
        return this.isAlarm;
    }
    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }
    public int getServerID() {
        return this.serverID;
    }
    public void setServerID(int serverID) {
        this.serverID = serverID;
    }


    
@Generated(hash = 958152791)
    public TaskBean(Long id, int serverID, String title, String context, int status, boolean isAlarm, Date time) {
        this.id = id;
        this.serverID = serverID;
        this.title = title;
        this.context = context;
        this.status = status;
        this.isAlarm = isAlarm;
        this.time = time;
    }
    @Generated(hash = 1443476586)
    public TaskBean() {
    }
   
}
