package com.example.daidaijie.syllabusapplication.recommendation.searchList;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.recommendation.bean.CourseBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.TeacherBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.finalResultBean;
import com.example.daidaijie.syllabusapplication.recommendation.mainMenu.RecomContract;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/4.
 */

public interface SearchListContract {
    interface presenter  extends BasePresenter {
        void showFinalResultByCourse(CourseBean course);
        void showFinalResultByTeacher(TeacherBean teacher);
    }
    interface view extends BaseView<RecomContract.presenter> {
        void showList(List<finalResultBean> unitBeen);
        void showMsg(String Msg);
        void showTips(String Msg);
        void closePage();
    }
}
