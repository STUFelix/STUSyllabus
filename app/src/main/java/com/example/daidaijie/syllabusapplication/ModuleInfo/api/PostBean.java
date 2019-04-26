package com.example.daidaijie.syllabusapplication.ModuleInfo.api;

import com.example.daidaijie.syllabusapplication.bean.CommentsBean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUp;

import java.util.List;

/**
 * Created by 16zhchen on 2019/3/9.
 */

public class PostBean {
    /**
     * id : 123
     * post_type : 3
     * content : 测试
     * post_time : 2019-03-02 19:23:58
     * source : 匿名
     * user : {"id":-1,"image":null,"nickname":"Anonymous","account":"Anonymous"}
     * description : 测试
     * thumb_ups : []
     * comments : []
     * topic : {"id":1,"name":"生活"}
     * photo_list_json : null
     * real_uid : 1
     * title : 测试
     */

    private int id;
    private int post_type;
    private String content;
    private String post_time;
    private String source;
    private UserBean user;
    private String description;
    private TopicBean topic;
    private String photo_list_json;
    private int real_uid;
    private String title;
    private List<ThumbUp> thumb_ups;
    private List<CommentsBean> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_type() {
        return post_type;
    }

    public void setPost_type(int post_type) {
        this.post_type = post_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TopicBean getTopic() {
        return topic;
    }

    public void setTopic(TopicBean topic) {
        this.topic = topic;
    }


    public int getReal_uid() {
        return real_uid;
    }

    public String getPhoto_list_json() {
        return photo_list_json;
    }

    public void setPhoto_list_json(String photo_list_json) {
        this.photo_list_json = photo_list_json;
    }

    public List<ThumbUp> getThumb_ups() {
        return thumb_ups;
    }

    public void setThumb_ups(List<ThumbUp> thumb_ups) {
        this.thumb_ups = thumb_ups;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public void setReal_uid(int real_uid) {
        this.real_uid = real_uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public static class UserBean {
        /**
         * id : -1
         * image : null
         * nickname : Anonymous
         * account : Anonymous
         */

        private int id;
        private Object image;
        private String nickname;
        private String account;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

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

    public static class TopicBean {
        /**
         * id : 1
         * name : 生活
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
