package com.example.daidaijie.syllabusapplication.login.splash;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;

/**
 * Created by daidaijie on 2016/10/13.
 */

public interface SplashContract {

    interface presenter extends BasePresenter {
    }

    interface view extends BaseView<presenter> {
        void toLoginView();

        void toMainView();
    }

}
