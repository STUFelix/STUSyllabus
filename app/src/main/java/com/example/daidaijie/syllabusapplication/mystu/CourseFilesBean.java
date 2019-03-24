package com.example.daidaijie.syllabusapplication.mystu;

import java.util.List;

public class CourseFilesBean {
    private int length;
    private List<CourseFilesDetailsBean> course_files_details;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<CourseFilesDetailsBean> getCourse_files_details() {
        return course_files_details;
    }

    public void setCourse_files_details(List<CourseFilesDetailsBean> course_files_details) {
        this.course_files_details = course_files_details;
    }

}