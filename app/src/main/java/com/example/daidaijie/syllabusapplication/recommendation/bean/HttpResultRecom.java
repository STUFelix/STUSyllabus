package com.example.daidaijie.syllabusapplication.recommendation.bean;

/**
 * Created by 16zhchen on 2018/10/17.
 */

public class HttpResultRecom<T> {

    /**
     * data : {"course_list":[{"cid":1105,"course_id":"CST3150","course_name":"编译原理","credit":3,"department":"计算机系","is_general":false,"total_hour":64},{"cid":2499,"course_id":"CST9302","course_name":"编译原理","credit":2.5,"department":"计算机系","is_general":false,"total_hour":48},{"cid":2756,"course_id":"CST3504A","course_name":"编译原理","credit":3,"department":"计算机系","is_general":false,"total_hour":60},{"cid":5991,"course_id":"JHKC7814","course_name":"编译原理","credit":4,"department":"校外开课","is_general":false,"total_hour":64}]}
     * pagination : {"total":4}
     */

    private T data;
    private PaginationBean pagination;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }



    public static class PaginationBean {
        /**
         * total : 4
         */

        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
