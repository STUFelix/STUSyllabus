package com.example.daidaijie.syllabusapplication.threeInfo.main;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.bean.UserInfo;

/**
 * Created by 16zhchen on 2018/10/28.
 */

public class InfoMainContract {
    interface presenter extends BasePresenter {
        UserInfo getUserInfo();
    }
    interface view extends BaseView<presenter> {

    }
}
