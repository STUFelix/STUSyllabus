package com.example.daidaijie.syllabusapplication.recommendation.bean;

import java.util.List;

/**
 * Q1M1,Q2M3
 */
public class CourseBean {
        /**
         * cid : 1105
         * course_id : CST3150
         * course_name : 编译原理
         * credit : 3
         * department : 计算机系
         * is_general : false
         * total_hour : 64
         */

        private int cid;
        private String course_id;
        private String course_name;
        private double credit;
        private String department;
        private boolean is_general;
        private int total_hour;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public double getCredit() {
            return credit;
        }

        public void setCredit(double credit) {
            this.credit = credit;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public boolean isIs_general() {
            return is_general;
        }

        public void setIs_general(boolean is_general) {
            this.is_general = is_general;
        }

        public int getTotal_hour() {
            return total_hour;
        }

        public void setTotal_hour(int total_hour) {
            this.total_hour = total_hour;
        }
    }

