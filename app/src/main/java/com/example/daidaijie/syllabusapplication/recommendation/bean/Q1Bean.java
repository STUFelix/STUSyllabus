package com.example.daidaijie.syllabusapplication.recommendation.bean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/20.
 */

public class Q1Bean<T> {
    private List<T> course_list;

    public List<T> getCourse_list() {
        return course_list;
    }

    public void setCourse_list(List<T> course_list) {
        this.course_list = course_list;
    }

}
