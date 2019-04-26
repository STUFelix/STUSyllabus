package com.example.daidaijie.syllabusapplication.threeInfo.bean;

import java.util.List;

import retrofit2.http.Url;

/**
 * Created by 16zhchen on 2018/10/27.
 */

public class InfoBean {
    String title;
    String content;
    int infoID;
    List<Url> photoList;
    int lovaNum;
    int commentNum;

    public InfoBean(String title, String content, int infoID, List<Url> photoList, int lovaNum, int commentNum) {
        this.title = title;
        this.content = content;
        this.infoID = infoID;
        this.photoList = photoList;
        this.lovaNum = lovaNum;
        this.commentNum = commentNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getInfoID() {
        return infoID;
    }

    public void setInfoID(int infoID) {
        this.infoID = infoID;
    }

    public List<Url> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Url> photoList) {
        this.photoList = photoList;
    }

    public int getLovaNum() {
        return lovaNum;
    }

    public void setLovaNum(int lovaNum) {
        this.lovaNum = lovaNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
}
