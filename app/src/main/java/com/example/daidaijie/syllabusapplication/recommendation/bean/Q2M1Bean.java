package com.example.daidaijie.syllabusapplication.recommendation.bean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/17.
 */

public class Q2M1Bean {

    /**
     * cid : 2756
     * teacher_list : [{"department":"计算机系","gender":true,"introduction":"博士(西北工业大学)、副教授、硕士生导师、2005年11月于国防科技大学完成博士后研究工作。主要研究方向：工作流、中间件和神经网络。开设的主要课程：数据库原理(本科)、计算科学导论(本科)和程序设计方法学(研究生)。","name":"李新","teacher_id":20685},{"department":"计算机系","gender":false,"introduction":"博士(华中科技大学)、副教授。主要研究方向：复杂系统建模与仿真，动态博弈等。开设的主要课程：计算科学导论(本科)、计算机组成与结构(本科)、编译原理(本科)和系统建模与仿真（研究生）。","name":"蔡玲如","teacher_id":133393}]
     */

    private int cid;
    private List<TeacherBean> teacher_list;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public List<TeacherBean> getTeacher_list() {
        return teacher_list;
    }

    public void setTeacher_list(List<TeacherBean> teacher_list) {
        this.teacher_list = teacher_list;
    }

}
