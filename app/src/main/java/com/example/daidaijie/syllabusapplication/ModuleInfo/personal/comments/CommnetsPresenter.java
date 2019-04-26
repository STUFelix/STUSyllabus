package com.example.daidaijie.syllabusapplication.ModuleInfo.personal.comments;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.CommnetBean;
import com.example.daidaijie.syllabusapplication.ModuleInfo.personal.ISchoolCircleModel;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class CommnetsPresenter implements CommnetsContract.presenter, CommentsAdapter.OnLongClickCallBack {

    ISchoolCircleModel mISchoolCircleModel;

    CommnetsContract.view mView;
    private static final String TAG = "CommnetsPresenter";
    IUserModel mIUserModel;

    @Inject
    @PerFragment
    public CommnetsPresenter(ISchoolCircleModel ISchoolCircleModel,
                             CommnetsContract.view view,
                             @LoginUser IUserModel userModel) {
        mISchoolCircleModel = ISchoolCircleModel;
        mView = view;
        mIUserModel = userModel;
    }

    @Override
    public void refresh() {
        mView.showRefresh(true);
        final int pageIndex = 1;
        mISchoolCircleModel.loadCommnetFromNet(pageIndex)
                .subscribe(new Subscriber<List<CommnetBean>>() {
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
                    public void onNext(List<CommnetBean> postListBeen) {
                        mView.showData(postListBeen);
                    }
                });
    }

    @Override
    public void loadData() {
        final int pageIndex = mView.getPage_index();
        mISchoolCircleModel.getCommnetFromNet(pageIndex)
                .subscribe(new Subscriber<List<CommnetBean>>() {
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
                    public void onNext(List<CommnetBean> postListBeen) {
                        if(postListBeen.size() != 0)
                            mView.showData(postListBeen);
                        else{
                            mView.stopLoad();
                        }
                    }
                });
    }


    @Override
    public void delete(int position) {
        mView.showLoading(true);
        mISchoolCircleModel.deleteComment(position)
                .subscribe(new Subscriber<List<CommnetBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.showLoading(false);
                        mView.showSuccessMessage("删除成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoading(false);
                        if (e.getMessage() == null) {
                            mView.showFailMessage("删除失败");
                        } else {
                            if (e.getMessage().toUpperCase().equals("UNAUTHORIZED")) {
                                mView.showFailMessage(App.getContext().getResources().getString(R.string.UNAUTHORIZED));
                            } else {
                                mView.showFailMessage(e.getMessage().toUpperCase());
                            }
                        }
                    }

                    @Override
                    public void onNext(List<CommnetBean> postListBeen) {
                        mView.showData(postListBeen);
                    }
                });
    }

    

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void onLongClick(final int position, final int mode) {
//        mISchoolCircleModel.getCircleByPosition(position, new IBaseModel.OnGetSuccessCallBack<PostListBean>() {
//            @Override
//            public void onGetSuccess(PostListBean postListBean) {
//                boolean canDelete = false;
//                if (mIUserModel.getUserBaseBeanNormal().getLevel() > 1 ||
//                        postListBean.getUser().getId() == mIUserModel.getUserBaseBeanNormal().getId()) {
//                    canDelete = true;
//                }
//                mView.showContentDialog(postListBean, mIUserModel.getUserBaseBeanNormal().getLevel() > 1,
//                        mode == CommentsAdapter.MODE_ITEM_CLICK && canDelete, position);
//            }
//        });
        Log.d(TAG, "onLongClick: ");
    }
}
