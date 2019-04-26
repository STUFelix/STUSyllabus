package com.example.daidaijie.syllabusapplication.threeInfo.info;

import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.threeInfo.ITinfoModel;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import javax.inject.Inject;

/**
 * Created by 16zhchen on 2018/11/25.
 */

public class InfoItemPresenter implements InfoItemContract.presenter,InfoItemAdapter.OnLikeCallBack{
    ITinfoModel mITindoModel;
    InfoItemContract.view mView;
    IUserModel mIUserModel;

    @Inject
    @PerFragment
    public InfoItemPresenter(ITinfoModel iTinfoModel,
                             InfoItemContract.view view,
                             @LoginUser IUserModel userModel){
        mITindoModel = iTinfoModel;
        mView = view;
        mIUserModel = userModel;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void start() {

    }

    @Override
    public void onLike(int position, boolean isLike, InfoItemAdapter.OnLikeStateChangeListener onLikeStateChangeListener) {

    }
}
