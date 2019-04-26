package com.example.daidaijie.syllabusapplication.recommendation.searchList;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.recommendation.IRecomModel;
import com.example.daidaijie.syllabusapplication.recommendation.bean.CourseBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q3ResultBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.TeacherBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.finalResultBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by 16zhchen on 2018/10/4.
 */

public class SearchListPrsenter implements SearchListContract.presenter{
    private IRecomModel mRecomModel;
    private SearchListContract.view mView;
    private List<finalResultBean> list ;
    finalResultBean bean;
    private static final String TAG = "SearchListPrsenter";

    @Inject
    @PerActivity
    public SearchListPrsenter(IRecomModel iRecomModel,SearchListContract.view view){
        mRecomModel = iRecomModel;
        mView = view;
    }

    @Override
    public void start() {
        list = new ArrayList<>();
    }


    @Override
    public void showFinalResultByCourse(final CourseBean course) {
        list.clear();
        mRecomModel.getOneCourseAllTeacherByQ2M1(course.getCid())
                .subscribe(new Subscriber<TeacherBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        if(list.size()==0){
                            mView.showTips("没有找到相关条目");
                            //mView.closePage();
                        }else
                            mView.showList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: length");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TeacherBean teacherBean) {
                        Log.d(TAG, "onNext: "+teacherBean.getName());
                        bean = new finalResultBean();
                        bean.setCourse_id(course.getCourse_id());
                        bean.setCourse_name(course.getCourse_name());
                        bean.setCredit(course.getCredit());
                        bean.setDepartment(course.getDepartment());
                        bean.setName(teacherBean.getName());
                        mRecomModel.getScore(course.getCid(),teacherBean.getTeacher_id())
                                .subscribe(new Subscriber<Q3ResultBean>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.d(TAG, "onCompleted: ");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        showErrorMsg(e);
                                    }

                                    @Override
                                    public void onNext(Q3ResultBean q3ResultBean) {
                                        bean.setAve_score(q3ResultBean.getAve_score());
                                    }
                                });
                        list.add(bean);
                    }
                });
    }

    @Override
    public void showFinalResultByTeacher(final TeacherBean teacher) {
        list.clear();
        mRecomModel.getOneThAllCourseByQ2M2(teacher.getTeacher_id())
                .subscribe(new Subscriber<CourseBean>() {
                    @Override
                    public void onCompleted() {

                        Log.d(TAG, "onCompleted: ");
                        if(list.size()==0){
                            mView.showTips("没有找到相关条目");
                            //mView.closePage();
                        }else
                            mView.showList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMsg(e);
                    }

                    @Override
                    public void onNext(CourseBean courseBean) {
                        bean  = new finalResultBean();
                        bean.setName(teacher.getName());
                        bean.setCourse_id(courseBean.getCourse_id());
                        bean.setCourse_name(courseBean.getCourse_name());
                        bean.setCredit(courseBean.getCredit());
                        bean.setDepartment(courseBean.getDepartment());
                        mRecomModel.getScore(courseBean.getCid(),teacher.getTeacher_id())
                                .subscribe(new Subscriber<Q3ResultBean>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.d(TAG, "onCompleted: ");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        showErrorMsg(e);
                                    }

                                    @Override
                                    public void onNext(Q3ResultBean q3ResultBean) {
                                        bean.setAve_score(q3ResultBean.getAve_score());
                                    }
                                });
                        list.add(bean);
                    }
                });
    }

    private void showErrorMsg(Throwable e){
        if(e instanceof HttpException){
            ResponseBody body =  ((HttpException) e).response().errorBody();
            try{
                mView.showMsg(body.string());
            }catch (IOException IOe) {
                IOe.printStackTrace();
            }
        }
    }
}
