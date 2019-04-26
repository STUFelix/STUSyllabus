package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/14.
 */

public class EvalAllBean {
    /**
     * data : {"eva_list":[{"eva_id":1,"class_id":96961,"teacher_id":20685,"cid":2756,"eva_content":"not bad","eva_tags":"good","eva_score":5,"eva_status":true,"eva_time":"2018-10-11 00:04:56","eva_years_semester":"2017-2018-1"},{"eva_id":7,"class_id":90644,"teacher_id":10162,"cid":5490,"eva_content":"not bad","eva_tags":"good","eva_score":8,"eva_status":true,"eva_time":"2018-10-11 00:48:59","eva_years_semester":"2016-2017-1"}]}
     * pagination : {"limit":2,"total":2}
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
        private List<EvaListBean> eva_list;

        public List<EvaListBean> getEva_list() {
            return eva_list;
        }

        public void setEva_list(List<EvaListBean> eva_list) {
            this.eva_list = eva_list;
        }

        public static class EvaListBean {
            /**
             * eva_id : 1
             * class_id : 96961
             * teacher_id : 20685
             * cid : 2756
             * eva_content : not bad
             * eva_tags : good
             * eva_score : 5
             * eva_status : true
             * eva_time : 2018-10-11 00:04:56
             * eva_years_semester : 2017-2018-1
             */

            private int eva_id;
            private int class_id;
            private int teacher_id;
            private int cid;
            private String eva_content;
            private String eva_tags;
            private int eva_score;
            private boolean eva_status;
            private String eva_time;
            private String eva_years_semester;

            public int getEva_id() {
                return eva_id;
            }

            public void setEva_id(int eva_id) {
                this.eva_id = eva_id;
            }

            public int getClass_id() {
                return class_id;
            }

            public void setClass_id(int class_id) {
                this.class_id = class_id;
            }

            public int getTeacher_id() {
                return teacher_id;
            }

            public void setTeacher_id(int teacher_id) {
                this.teacher_id = teacher_id;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getEva_content() {
                return eva_content;
            }

            public void setEva_content(String eva_content) {
                this.eva_content = eva_content;
            }

            public String getEva_tags() {
                return eva_tags;
            }

            public void setEva_tags(String eva_tags) {
                this.eva_tags = eva_tags;
            }

            public int getEva_score() {
                return eva_score;
            }

            public void setEva_score(int eva_score) {
                this.eva_score = eva_score;
            }

            public boolean isEva_status() {
                return eva_status;
            }

            public void setEva_status(boolean eva_status) {
                this.eva_status = eva_status;
            }

            public String getEva_time() {
                return eva_time;
            }

            public void setEva_time(String eva_time) {
                this.eva_time = eva_time;
            }

            public String getEva_years_semester() {
                return eva_years_semester;
            }

            public void setEva_years_semester(String eva_years_semester) {
                this.eva_years_semester = eva_years_semester;
            }
        }
    }

    public static class PaginationBean {
        /**
         * limit : 2
         * total : 2
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
