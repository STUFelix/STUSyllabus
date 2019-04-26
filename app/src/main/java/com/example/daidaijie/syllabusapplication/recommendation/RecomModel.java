package com.example.daidaijie.syllabusapplication.recommendation;

import com.example.daidaijie.syllabusapplication.recommendation.bean.CourseBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.HttpResultRecom;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q1Bean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q2M1Bean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q2M2Bean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q3ResultBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.TeacherBean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 16zhchen on 2018/10/4.
 */

public class RecomModel implements IRecomModel{

    recomApi mRecomApi;
    private static final String TAG = "RecomModel";

    public RecomModel(recomApi recomApi){
        mRecomApi = recomApi;
    }

    @Override
    public Observable<Q3ResultBean> getScore(int cid, int teacherid) {
        return mRecomApi.Q3_getScore(cid,teacherid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<CourseBean>> getAllCourseByQ1M1(String keyword) {
        return mRecomApi.Q1_getfuzzyResultBym1(1,keyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<HttpResultRecom<Q1Bean<CourseBean>>, List<CourseBean>>() {
                    @Override
                    public List<CourseBean> call(HttpResultRecom<Q1Bean<CourseBean>> q1BeanHttpResultRecom) {
                        return q1BeanHttpResultRecom.getData().getCourse_list();
                    }
                });
    }

    @Override
    public Observable<TeacherBean> getOneCourseAllTeacherByQ2M1(int cid) {
        return mRecomApi.Q2_getDetailResultBym1(1,cid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpResultRecom<Q2M1Bean>, Observable<TeacherBean>>() {
                    @Override
                    public Observable<TeacherBean> call(HttpResultRecom<Q2M1Bean> q2M1BeanHttpResultRecom) {
                        return Observable.from(q2M1BeanHttpResultRecom.getData().getTeacher_list());
                    }
                });
    }

    @Override
    public Observable<List<TeacherBean>> getAllteacherByQ1M2(String keyword) {
        return mRecomApi.Q1_getfuzzyResultBym2(2,keyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<HttpResultRecom<Q1Bean<TeacherBean>>, List<TeacherBean>>() {
                    @Override
                    public List<TeacherBean> call(HttpResultRecom<Q1Bean<TeacherBean>> q1BeanHttpResultRecom) {
                        return q1BeanHttpResultRecom.getData().getCourse_list();
                    }
                });
    }

    @Override
    public Observable<CourseBean> getOneThAllCourseByQ2M2(int teacherID) {
        return mRecomApi.Q2_getDetailResultBym2(2,teacherID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpResultRecom<Q2M2Bean>, Observable<CourseBean>>() {
                    @Override
                    public Observable<CourseBean> call(HttpResultRecom<Q2M2Bean> q2M2BeanHttpResultRecom) {
                        return Observable.from(q2M2BeanHttpResultRecom.getData().getCourse_list());
                    }
                });
    }

    @Override
    public Observable<List<String>> getAllUnitByQ1M3(String keyword) {
        return mRecomApi.Q1_getfuzzyResultBym3(3,keyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<HttpResultRecom<Q1Bean<String>>, List<String>>() {
                    @Override
                    public List<String> call(HttpResultRecom<Q1Bean<String>> q1BeanHttpResultRecom) {
                        return q1BeanHttpResultRecom.getData().getCourse_list();
                    }
                });
    }

    @Override
    public Observable<List<CourseBean>> getOneUnitAllCourseByQ2M3(String department) {
        return mRecomApi.Q2_getDetailResultBym3(3,department)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<HttpResultRecom<Q1Bean<CourseBean>>, List<CourseBean>>() {
                    @Override
                    public List<CourseBean> call(HttpResultRecom<Q1Bean<CourseBean>> q1BeanHttpResultRecom) {
                        return q1BeanHttpResultRecom.getData().getCourse_list();
                    }
                });
    }


}
