package com.example.daidaijie.syllabusapplication.recommendation.mainMenu;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.recommendation.IRecomModel;
import com.example.daidaijie.syllabusapplication.recommendation.bean.BaseAdapterBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.CourseBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.TeacherBean;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by 16zhchen on 2018/10/4.
 */

public class RecomPresenter implements RecomContract.presenter {
    private IRecomModel mRecomModel;
    private RecomContract.view mView;
    private int Mode = 1;
    private static final String TAG = "RecomPresenter";


    @Inject
    @PerActivity
    public RecomPresenter(IRecomModel iRecomModel,RecomContract.view view){
        mRecomModel = iRecomModel;
        mView = view;
    }

    @Override
    public void start() {
        //list = new ArrayList<>();
    }


    @Override
    public void getAllCourseByQ1M1(String keyword) {
        final BaseAdapterBean<CourseBean> bean = new BaseAdapterBean<>();
        mRecomModel.getAllCourseByQ1M1(keyword)
                .subscribe(new Subscriber<List<CourseBean>>() {
                    @Override
                    public void onCompleted() {
                        bean.setType(RecomItemAdapter.Q1M1);
                        mView.showList(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMsg(e);
                    }

                    @Override
                    public void onNext(List<CourseBean> courseBeen) {
                        bean.setData(courseBeen);
                    }
                });
    }

    @Override
    public void getAllTeacherByQ1M2(String keyword) {
        final BaseAdapterBean<TeacherBean> bean = new BaseAdapterBean<>();
        mRecomModel.getAllteacherByQ1M2(keyword)
                .subscribe(new Subscriber<List<TeacherBean>>() {
                    @Override
                    public void onCompleted() {
                        bean.setType(RecomItemAdapter.Q1M2);
                        mView.showList(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMsg(e);
                    }

                    @Override
                    public void onNext(List<TeacherBean> teacherBeen) {
                        bean.setData(teacherBeen);
                        Log.d(TAG, "onNext: "+teacherBeen.size());
                    }
                });
    }

    @Override
    public void getAllUnitByQ1M3(String keyword) {
        final BaseAdapterBean<String> bean = new BaseAdapterBean<>();
        mRecomModel.getAllUnitByQ1M3(keyword)
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        bean.setType(RecomItemAdapter.Q1M3);
                        mView.showList(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMsg(e);
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        bean.setData(strings);
                    }
                });
    }

    @Override
    public void getAllCourseBydepartByQ2M3(String department) {
        final BaseAdapterBean<CourseBean> bean = new BaseAdapterBean<>();
        mRecomModel.getOneUnitAllCourseByQ2M3(department)
                .subscribe(new Subscriber<List<CourseBean>>() {
                    @Override
                    public void onCompleted() {
                        bean.setType(RecomItemAdapter.Q1M1);
                        //setMode(1);
                        mView.showList(bean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<CourseBean> courseBeen) {
                        bean.setData(courseBeen);
                    }
                });
    }

    @Override
    public void setMode(int mode) {
        this.Mode = mode;
    }

    @Override
    public int getMode() {
        return Mode;
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
