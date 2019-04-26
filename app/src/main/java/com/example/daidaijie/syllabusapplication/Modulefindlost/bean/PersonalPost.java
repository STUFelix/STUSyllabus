package com.example.daidaijie.syllabusapplication.Modulefindlost.bean;

import java.io.Serializable;

/**
 * Created by 16zhchen on 2019/2/20.
 */

public class PersonalPost implements Serializable{

    /**
     * id : 11
     * uid : 1
     * release_time : 2019-01-30 13:33:37
     * title : 000
     * location : 000
     * description : 000
     * contact : 000
     * img_link : null
     * kind : 1
     * status : false
     */

    private int id;
    private int uid;
    private String release_time;
    private String title;
    private String location;
    private String description;
    private String contact;
    private String img_link;
    private int kind;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
