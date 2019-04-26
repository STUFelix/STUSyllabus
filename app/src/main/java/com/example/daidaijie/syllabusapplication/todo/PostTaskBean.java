package com.example.daidaijie.syllabusapplication.todo;

/**
 * Created by 16zhchen on 2018/10/3.
 */

public class PostTaskBean {
    int type;
    String title;
    String context;
    int status;
    public PostTaskBean(String t,String c,int ty,int s){
        type = ty;
        title = t;
        context = c;
        status = s;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
