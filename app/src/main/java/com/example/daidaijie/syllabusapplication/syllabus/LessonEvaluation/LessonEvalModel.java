package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.syllabus.ISyllabusModel;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.EvalAllBean;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.EvalBeanSimple;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.PostEvalBean;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 16zhchen on 2018/9/22.
 */

public class LessonEvalModel implements ILessonEvalModel {
    private static final String TAG = "LessonEvalModel";
    EvalApi mEvalApi;
    IUserModel mUserModel;
    ISyllabusModel mISyllabusModel;
    public LessonEvalModel(EvalApi evalApi, ISyllabusModel iSyllabusModel){
        mEvalApi = evalApi;
        mISyllabusModel = iSyllabusModel;
        mUserModel = mISyllabusModel.getmIUserModel();


    }

    public ISyllabusModel getmISyllabusModel() {
        return mISyllabusModel;
    }


    @Override
    public Observable<EvalAllBean.DataBean.EvaListBean> getAllEval() {
        //仅在LessonDetailPresenter中调用一次，存储到数据库中
        return mEvalApi.getAllLessonEval(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),1,1,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<EvalAllBean, Observable<EvalAllBean.DataBean.EvaListBean>>() {
                    @Override
                    public Observable<EvalAllBean.DataBean.EvaListBean> call(EvalAllBean evalAllBean) {
                        return Observable.from(evalAllBean.getData().getEva_list());
                    }
                });
    }

    @Override
    public Observable<HttpBean> addNewEval(int score, String content, int classID, String tags) {
        //TODO,处理tags
        return mEvalApi.addLessonEval(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),classID,score,"",content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpBean> editEval(int evaID, int score, String tags, String content) {
        return mEvalApi.editLessonEval(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),evaID,score,tags,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpBean> deleteEval(int evaID) {
        return mEvalApi.deleteTask(mUserModel.getUserInfoNormal().getUser_id(),mUserModel.getUserInfoNormal().getToken(),evaID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
