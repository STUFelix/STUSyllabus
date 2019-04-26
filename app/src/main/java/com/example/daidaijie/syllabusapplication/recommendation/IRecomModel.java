package com.example.daidaijie.syllabusapplication.recommendation;

import com.example.daidaijie.syllabusapplication.recommendation.bean.CourseBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q3ResultBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.TeacherBean;

import java.util.List;

import rx.Observable;

/**
 * Created by 16zhchen on 2018/10/4.
 */

public interface IRecomModel {


    Observable<Q3ResultBean> getScore(int cid,int teacherid);

    Observable<List<CourseBean>> getAllCourseByQ1M1(String keyword);
    Observable<TeacherBean> getOneCourseAllTeacherByQ2M1(int cid);

    Observable<List<TeacherBean>> getAllteacherByQ1M2(String keyword);
    Observable<CourseBean> getOneThAllCourseByQ2M2(int teacherID);

    Observable<List<String>> getAllUnitByQ1M3(String keyword);
    Observable<List<CourseBean>> getOneUnitAllCourseByQ2M3(String department);
 }
