package com.example.daidaijie.syllabusapplication.syllabus.lessonDetail;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.base.IBaseModel;
import com.example.daidaijie.syllabusapplication.bean.Syllabus;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.SchoolRetrofit;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.syllabus.ISyllabusModel;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.EvalApi;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.EvalAllBean;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.local.EvalBean;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.local.EvalDataManager;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/20.
 */

public class LessonInfoPresenter implements LessonInfoContract.presenter {

    long mLessonId;

    LessonInfoContract.view mView;

    ISyllabusModel mISyllabusModel;


    List<EvalBean> evalList = new ArrayList<>();
    private static final String TAG = "LessonEvalPresenter";
    private EvalDataManager dataManager;

    @PerActivity
    @Inject
    public LessonInfoPresenter(long lessonId, LessonInfoContract.view view, ISyllabusModel syllabusModel) {
        mLessonId = lessonId;
        mView = view;
        mISyllabusModel = syllabusModel;

    }

    @Override
    public void start() {
        mISyllabusModel.getSyllabusNormal(new IBaseModel.OnGetSuccessCallBack<Syllabus>() {
            @Override
            public void onGetSuccess(Syllabus syllabus) {
                mView.showData(syllabus.getLessonByID(mLessonId));
            }
        }, null);

    }
    @Override
    public void getAllEval() {
        IUserModel mUserModel = mISyllabusModel.getmIUserModel();
        evalList = new ArrayList<>();
        Log.d(TAG, "getAllEval: ");
        //TODO,多一段获取用户信息的代码
        dataManager = EvalDataManager.getInstance();
        @SchoolRetrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://class.stuapps.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EvalApi api = retrofit.create(EvalApi.class);
        api.getAllLessonEval(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),1,1,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<EvalAllBean, Observable<EvalAllBean.DataBean.EvaListBean>>() {
                    @Override
                    public Observable<EvalAllBean.DataBean.EvaListBean> call(EvalAllBean evalAllBean) {
                        return Observable.from(evalAllBean.getData().getEva_list());
                    }
                }).subscribe(new Subscriber<EvalAllBean.DataBean.EvaListBean>() {
            @Override
            public void onCompleted() {
                dataManager.deleteAll();
                dataManager.inseartAll(evalList);
                Log.d(TAG, "onCompleted: "+dataManager.dbCount());
            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof HttpException){
                    ResponseBody body =  ((HttpException) e).response().errorBody();
                    try{
                        Log.d(TAG, "onError: "+body.string());
                    }catch (IOException IOe) {
                        IOe.printStackTrace();
                    }
                }
            }

            @Override
            public void onNext(EvalAllBean.DataBean.EvaListBean evaL) {

                EvalBean t = new EvalBean(null,evaL.getEva_id(),evaL.getClass_id(),evaL.getTeacher_id(),
                        evaL.getCid(),evaL.getEva_content(),evaL.getEva_tags(),evaL.getEva_score(),
                        evaL.isEva_status(),evaL.getEva_time(),evaL.getEva_years_semester());
                evalList.add(t);
            }
        });
    }
}
