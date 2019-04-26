package com.example.daidaijie.syllabusapplication.recommendation.mainMenu;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.recommendation.bean.BaseAdapterBean;

/**
 * Created by 16zhchen on 2018/10/4.
 */

public interface RecomContract {
    interface presenter  extends BasePresenter {
        void getAllCourseByQ1M1(String keyword);
        void getAllTeacherByQ1M2(String keyword);
        void getAllUnitByQ1M3(String keyword);
        void getAllCourseBydepartByQ2M3(String department);
        void setMode(int mode);
        int getMode();

    }
    interface view extends BaseView<RecomContract.presenter> {
        void showMsg(String Msg);
        void showList(BaseAdapterBean bean);
    }
}
