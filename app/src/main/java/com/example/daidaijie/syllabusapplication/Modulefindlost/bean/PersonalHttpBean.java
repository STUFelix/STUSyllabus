package com.example.daidaijie.syllabusapplication.Modulefindlost.bean;

import java.util.List;

/**
 * Created by 16zhchen on 2019/2/2.
 */

public class PersonalHttpBean {

    /**
     * data : {"findlost_list":[{"id":11,"uid":1,"release_time":"2019-01-30 13:33:37","title":"000","location":"000","description":"000","contact":"000","img_link":null,"kind":1,"status":false}]}
     * pagination : {"limit":1,"total":1}
     */

    private DataBean data;
    private PaginationBean pagination;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public static class DataBean {
        private List<PersonalPost> findlost_list;

        public List<PersonalPost> getFindlost_list() {
            return findlost_list;
        }

        public void setFindlost_list(List<PersonalPost> findlost_list) {
            this.findlost_list = findlost_list;
        }


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
}
