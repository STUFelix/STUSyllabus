package com.example.daidaijie.syllabusapplication.Modulefindlost.bean;

/**
 * Created by 16zhchen on 2019/2/20.
 */

public class postBean {
    int uid;
    String token;
    int kind;
    String title;
    String description;
    String location;
    String contact;
    String findlost_time;
    String img_link;

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setFindlost_time(String findlost_time) {
        this.findlost_time = findlost_time;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }
}
