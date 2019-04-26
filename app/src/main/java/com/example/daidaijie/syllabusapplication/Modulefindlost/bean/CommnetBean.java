package com.example.daidaijie.syllabusapplication.Modulefindlost.bean;

/**
 * Created by 16zhchen on 2019/2/2.
 */

public class CommnetBean {
    /**
     * id : 30
     * post_time : 2019-02-02 16:12:13
     * comment : 评论测试
     * post_id : 84
     * uid : 3
     * user : {"nickname":"16zhchen8","account":"16zhchen8"}
     */

    private int id;
    private String post_time;
    private String comment;
    private int post_id;
    private int uid;
    private UserBean user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * nickname : 16zhchen8
         * account : 16zhchen8
         */

        private String nickname;
        private String account;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }
}
