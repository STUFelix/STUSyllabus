package com.example.daidaijie.syllabusapplication.recommendation.bean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/17.
 */

public class Q2M2Bean {

    /**
     * course_list : [{"cid":2756,"course_id":"CST3504A","course_name":"编译原理","credit":3,"department":"计算机系","is_general":false,"total_hour":60},{"cid":3523,"course_id":"CST1501A","course_name":"计算科学导论","credit":2,"department":"计算机系","is_general":false,"total_hour":38},{"cid":4819,"course_id":"CST2104A","course_name":"计算机组织与体系结构II","credit":3,"department":"计算机系","is_general":false,"total_hour":48}]
     * teacher_id : 133393
     */

    private int teacher_id;
    private List<CourseBean> course_list;

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public List<CourseBean> getCourse_list() {
        return course_list;
    }

    public void setCourse_list(List<CourseBean> course_list) {
        this.course_list = course_list;
    }


}
