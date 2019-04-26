package com.example.daidaijie.syllabusapplication.recommendation.bean;

/**
 * Created by 16zhchen on 2018/10/17.
 */

public class finalResultBean {
    private String course_id;
    private String course_name;
    private String department;
    private double credit;

    private String name;
    private double ave_score;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAve_score() {
        return ave_score;
    }

    public void setAve_score(double ave_score) {
        this.ave_score = ave_score;
    }
}
