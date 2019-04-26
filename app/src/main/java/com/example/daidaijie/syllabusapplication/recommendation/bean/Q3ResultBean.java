package com.example.daidaijie.syllabusapplication.recommendation.bean;

/**
 * Created by 16zhchen on 2018/10/17.
 */

public class Q3ResultBean {
    /**
     * ave_score : null
     * ave_tags : null
     * cid : 2756
     * id : 2421
     * teacher_id : 133393
     */

    private double ave_score;
    private String ave_tags;
    private int cid;
    private int id;
    private int teacher_id;

    public double getAve_score() {
        return ave_score;
    }

    public void setAve_score(double ave_score) {
        this.ave_score = ave_score;
    }

    public String getAve_tags() {
        return ave_tags;
    }

    public void setAve_tags(String ave_tags) {
        this.ave_tags = ave_tags;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
}
