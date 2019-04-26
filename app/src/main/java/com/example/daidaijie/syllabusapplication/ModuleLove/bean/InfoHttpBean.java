package com.example.daidaijie.syllabusapplication.ModuleLove.bean;


import com.example.daidaijie.syllabusapplication.bean.PostListBean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/12/14.
 */

public class InfoHttpBean {
    /**
     * data : [{"id":79,"post_type":0,"content":"表白墙测试10","post_time":"2019-01-29 17:53:04","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[{"id":18,"uid":5}],"comments":[{"id":18,"uid":5},{"id":19,"uid":5},{"id":20,"uid":5},{"id":21,"uid":5},{"id":22,"uid":5},{"id":23,"uid":5},{"id":24,"uid":5},{"id":25,"uid":5},{"id":26,"uid":5},{"id":27,"uid":5},{"id":28,"uid":5}],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":78,"post_type":0,"content":"表白墙测试9","post_time":"2019-01-29 17:52:55","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[{"id":19,"uid":5}],"comments":[],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":77,"post_type":0,"content":"表白墙测试8","post_time":"2019-01-29 17:52:45","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[],"comments":[{"id":29,"uid":5}],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":76,"post_type":0,"content":"表白墙测试7","post_time":"2019-01-29 17:52:36","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[],"comments":[],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":75,"post_type":0,"content":"表白墙测试6","post_time":"2019-01-29 17:52:26","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[],"comments":[],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":74,"post_type":0,"content":"表白墙测试5","post_time":"2019-01-29 17:52:18","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[],"comments":[],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":73,"post_type":0,"content":"表白墙测试4","post_time":"2019-01-29 17:52:03","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[],"comments":[],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":72,"post_type":0,"content":"表白墙测试3","post_time":"2019-01-29 17:51:46","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[],"comments":[],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":71,"post_type":0,"content":"表白墙测试2","post_time":"2019-01-29 17:51:31","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[],"comments":[],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null},{"id":70,"post_type":0,"content":"表白墙测试1","post_time":"2019-01-29 17:50:45","source":"小程序","user":{"id":5,"image":null,"nickname":"16eyhuang","account":"16eyhuang"},"description":"表白墙","thumb_ups":[],"comments":[],"topic":{"id":6,"name":"表白墙"},"photo_list_json":null,"real_uid":0,"title":null}]
     * pagination : {"limit":10,"total":52}
     */

    private PaginationBean pagination;
    private List<PostListBean> data;

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public List<PostListBean> getData() {
        return data;
    }

    public void setData(List<PostListBean> data) {
        this.data = data;
    }

    public static class PaginationBean {
        /**
         * limit : 10
         * total : 52
         */

        private int limit;
        private int total;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
