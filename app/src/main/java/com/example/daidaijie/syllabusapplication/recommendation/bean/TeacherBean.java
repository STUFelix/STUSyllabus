package com.example.daidaijie.syllabusapplication.recommendation.bean;

/**
 * Created by 16zhchen on 2018/10/17.
 */

public class TeacherBean {

    /**
     * department : 计算机系
     * gender : true
     * introduction : 博士(西北工业大学)、副教授、硕士生导师、2005年11月于国防科技大学完成博士后研究工作。主要研究方向：工作流、中间件和神经网络。开设的主要课程：数据库原理(本科)、计算科学导论(本科)和程序设计方法学(研究生)。
     * name : 李新
     * teacher_id : 20685
     */

    private String department;
    private boolean gender;
    private String introduction;
    private String name;
    private int teacher_id;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
}
