package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.mainmenu;

import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;
import com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.FindLostAdapter;
import com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.IFindLostModel;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class FindLostPresenter implements FindLostContract.presenter, FindLostAdapter.OnLongClickCallBack {

    IFindLostModel mIFindLostModel;

    FindLostContract.view mView;

    IUserModel mIUserModel;

    @Inject
    @PerFragment
    public FindLostPresenter(IFindLostModel IFindLostModel,
                             FindLostContract.view view,
                             @LoginUser IUserModel userModel) {
        mIFindLostModel = IFindLostModel;
        mView = view;
        mIUserModel = userModel;
    }

    @Override
    public void refresh() {
        mView.showRefresh(true);
        final int pageIndex = 1;
        mIFindLostModel.getFindLostListFromNet(mView.getTAB(),pageIndex)
                .subscribe(new Subscriber<List<FindBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.showRefresh(false);
                        mView.setPage_index(pageIndex+1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.printStack(e);
                        mView.showRefresh(false);
                        if (e.getMessage() == null) {
                            mView.showFailMessage("获取失败");
                        } else {
                            mView.showFailMessage(e.getMessage().toUpperCase());
                        }
                    }

                    @Override
                    public void onNext(List<FindBean> postListBeen) {
                        mView.showData(postListBeen);
                    }
                });
    }

    @Override
    public void loadData() {
        final int pageIndex = mView.getPage_index();
        mIFindLostModel.getFindLostListFromNet(mView.getTAB(),pageIndex)
                .subscribe(new Subscriber<List<FindBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.loadMoreFinish();
                        mView.setPage_index(pageIndex+1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage() == null) {
                            mView.showFailMessage("获取失败");
                        } else {
                            mView.showFailMessage(e.getMessage().toUpperCase());
                        }
                        mView.loadMoreFinish();
                    }

                    @Override
                    public void onNext(List<FindBean> postListBeen) {
                        if(postListBeen.size() != 0)
                            mView.showData(postListBeen);
                        else{
                            mView.stopLoad();
                        }
                    }
                });
    }


    @Override
    public void start() {
        refresh();
    }

    @Override
    public void onLongClick(final int position, final int mode) {

    }
}
