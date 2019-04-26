package com.example.daidaijie.syllabusapplication.threeInfo.main;

import com.example.daidaijie.syllabusapplication.bean.UserInfo;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.di.scope.PerModule;
import com.example.daidaijie.syllabusapplication.di.scope.PerUser;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import javax.inject.Inject;


public class InfoMainPresenter implements  InfoMainContract.presenter{

    private UserInfo mUserInfo;

    @Inject
    @PerFragment
    public  InfoMainPresenter(@LoginUser IUserModel iUserModel){
        mUserInfo = iUserModel.getUserInfoNormal();
    }


    @Override
    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    @Override
    public void start() {
    }
}
