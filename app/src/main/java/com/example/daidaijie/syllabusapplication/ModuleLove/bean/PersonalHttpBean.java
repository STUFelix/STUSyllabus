package com.example.daidaijie.syllabusapplication.ModuleLove.bean;

import java.util.List;

/**
 * Created by 16zhchen on 2019/2/2.
 */

public class PersonalHttpBean {

    /**
     * data : [{"id":30,"post_time":"2019-02-02 16:12:13","comment":"评论测试","post_id":84,"uid":3,"user":{"nickname":"16zhchen8","account":"16zhchen8"}}]
     * pagination : {"limit":1,"total":1}
     */

    private PaginationBean pagination;
    private List<CommnetBean> data;

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public List<CommnetBean> getData() {
        return data;
    }

    public void setData(List<CommnetBean> data) {
        this.data = data;
    }

    public static class PaginationBean {
        /**
         * limit : 1
         * total : 1
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

    public static class DataBean {
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
}
