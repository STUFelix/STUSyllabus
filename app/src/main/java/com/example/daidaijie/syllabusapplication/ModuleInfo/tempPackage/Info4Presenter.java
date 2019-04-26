package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.Info4Bean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by 16zhchen on 2018/12/14.
 */

public class Info4Presenter implements Info4Contract.presenter,Info4Adapter.OnLongClickCallBack {
    private IInfoModel mInfo4Model;
    private Info4Contract.view mView;
    @Inject
    @PerFragment
    public Info4Presenter(IInfoModel iInfoModel,Info4Contract.view view){
        mInfo4Model = iInfoModel;
        mView = view;
    }
    @Override
    public void refresh() {
        mView.showRefresh(false);
        mInfo4Model.getInfo()
                .subscribe(new Subscriber<List<Info4Bean>>() {
                    @Override
                    public void onCompleted() {
                        mView.showRefresh(false);
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
                    public void onNext(List<Info4Bean> info4Been) {
                        mView.showData(info4Been);
                    }
                });
    }

    @Override
    public void loadData() {
        mInfo4Model.getInfo()
                .subscribe(new Subscriber<List<Info4Bean>>() {
                    @Override
                    public void onCompleted() {
                        mView.loadMoreFinish();
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
                    public void onNext(List<Info4Bean> info4Been) {
                        mView.showData(info4Been);
                    }
                });
    }
    public void loadOnePage(int pageIndex){
        mInfo4Model.getOnePageInfo(pageIndex)
                .subscribe(new Subscriber<List<Info4Bean>>() {
                    @Override
                    public void onCompleted() {
                        mView.loadMoreFinish();
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
                    public void onNext(List<Info4Bean> info4Been) {
                        mView.showData(info4Been);
                    }
                });
    }
    @Override
    public void handlerFAB() {

    }

    @Override
    public void deletePost(int position) {

    }

    @Override
    public void start() {

    }

    @Override
    public void onLike(int position, boolean isLike, final Info4Adapter.OnLikeStateChangeListener onLikeStateChangeListener) {
        if(isLike){
            mInfo4Model.like(position)
                    .subscribe(new Subscriber<ThumbUpReturn>() {
                        @Override
                        public void onCompleted() {
                            onLikeStateChangeListener.onFinish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            onLikeStateChangeListener.onFinish();
                        }

                        @Override
                        public void onNext(ThumbUpReturn thumbUpReturn) {
                            onLikeStateChangeListener.onLike(true);
                        }
                    });
        }else{
            mInfo4Model.unlike(position)
                    .subscribe(new Subscriber<ThumbUpReturn>() {
                        @Override
                        public void onCompleted() {
                            onLikeStateChangeListener.onFinish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            onLikeStateChangeListener.onFinish();
                        }

                        @Override
                        public void onNext(ThumbUpReturn thumbUpReturn) {
                            onLikeStateChangeListener.onLike(false);
                        }
                    });
        }
    }

    @Override
    public void onLongClick(int position, int mode) {

    }
}
