package com.example.daidaijie.syllabusapplication.bean;

import java.util.Date;

/**
 * Created by 16zhchen on 2018/9/16.
 */

public class EvaluationBean {
    int id;
    int teachaer_course;
    Date time;
    String content;
    String stu_account;
    String tags;
    int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeachaer_course() {
        return teachaer_course;
    }

    public void setTeachaer_course(int teachaer_course) {
        this.teachaer_course = teachaer_course;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStu_account() {
        return stu_account;
    }

    public void setStu_account(String stu_account) {
        this.stu_account = stu_account;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    int status;
}
