package com.example.daidaijie.syllabusapplication.todo.bean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/14.
 */

public class TODOAllBean {
    /**
     * data : {"eva_list":[{"id":4,"label":"学习","title":"0","content":"0","release_time":"2018-10-09 11:16:57","start_time":"2018-09-12 18:18:18","deadline_time":"2018-09-15 18:18:18","priority":0,"status":true,"img_link":""},{"id":6,"label":"学习","title":"0","content":"0","release_time":"2018-10-10 21:03:30","start_time":"2018-09-18 18:18:19","deadline_time":"2018-09-10 18:18:00","priority":0,"status":false,"img_link":""}]}
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
             * id : 4
             * label : 学习
             * title : 0
             * content : 0
             * release_time : 2018-10-09 11:16:57
             * start_time : 2018-09-12 18:18:18
             * deadline_time : 2018-09-15 18:18:18
             * priority : 0
             * status : true
             * img_link :
             */

            private int id;
            private String label;
            private String title;
            private String content;
            private String release_time;
            private String start_time;
            private String deadline_time;
            private int priority;
            private boolean status;
            private String img_link;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
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

            public String getRelease_time() {
                return release_time;
            }

            public void setRelease_time(String release_time) {
                this.release_time = release_time;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getDeadline_time() {
                return deadline_time;
            }

            public void setDeadline_time(String deadline_time) {
                this.deadline_time = deadline_time;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public String getImg_link() {
                return img_link;
            }

            public void setImg_link(String img_link) {
                this.img_link = img_link;
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
