package com.example.daidaijie.syllabusapplication.ModuleInfo.bean;

import com.example.daidaijie.syllabusapplication.bean.CommentsBean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpsBean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/12/14.
 */

public class Info4Bean {

    public boolean isMylove = false;

        /**
         * id : 2
         * post_type : 0
         * content : 测试
         * post_time : 2018-12-12 21:48:58
         * source : 匿名
         * user : {"id":1,"image":null,"nickname":"16ypwang","account":"16ypwang"}
         * description : 测试
         * thumb_ups : []
         * comments : []
         * topic : {"id":0,"name":"其他"}
         * photo_list_json : null
         * real_uid : 0
         * title : null
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
        private List<ThumbUpsBean> thumb_ups;
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

        public void setReal_uid(int real_uid) {
            this.real_uid = real_uid;
        }





    public void setPhoto_list_json(String photo_list_json) {
        this.photo_list_json = photo_list_json;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumb_ups(List<ThumbUpsBean> thumb_ups) {
        this.thumb_ups = thumb_ups;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class UserBean {
            /**
             * id : 1
             * image : null
             * nickname : 16ypwang
             * account : 16ypwang
             */

            private int id;
            private String image;
            private String nickname;
            private String account;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
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

    public String getPhoto_list_json() {
        return photo_list_json;
    }

    public String getTitle() {
        return title;
    }

    public List<ThumbUpsBean> getThumb_ups() {
        return thumb_ups;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public static class TopicBean {
            /**
             * id : 0
             * name : 其他
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
