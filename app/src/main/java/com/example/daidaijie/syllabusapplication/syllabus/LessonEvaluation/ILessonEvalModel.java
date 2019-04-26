package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.syllabus.ISyllabusModel;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.EvalAllBean;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.EvalBeanSimple;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;

import rx.Observable;

/**
 * 处理数据的接口
 */

public interface ILessonEvalModel {
//    Observable<HttpResult<EvalBeanSimple>> getEvaluationFromNet(long classID);
//    Observable<HttpResult<String>> sendEvaluationToNet(int score, String content,long classID);
    ISyllabusModel getmISyllabusModel();

    Observable<EvalAllBean.DataBean.EvaListBean> getAllEval();
    Observable<HttpBean> addNewEval(int score, String content,int classID,String tags);
    Observable<HttpBean> editEval(int evaID,int score,String tags,String content);
    Observable<HttpBean> deleteEval(int evaID);
}
