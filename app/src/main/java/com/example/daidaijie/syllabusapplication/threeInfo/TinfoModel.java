package com.example.daidaijie.syllabusapplication.threeInfo;


import com.example.daidaijie.syllabusapplication.user.IUserModel;

/**
 * Created by 16zhchen on 2018/10/28.
 */

public class TinfoModel implements ITinfoModel {

    IUserModel mUserModel;

    public TinfoModel(IUserModel user){
        //添加api
        mUserModel = user;
    }

    @Override
    public IUserModel getmIUserModel() {
        return mUserModel;
    }
}