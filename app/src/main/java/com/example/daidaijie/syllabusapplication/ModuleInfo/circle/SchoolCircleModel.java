package com.example.daidaijie.syllabusapplication.ModuleInfo.circle;

import com.example.daidaijie.syllabusapplication.ModuleInfo.api.CirclesApi;
import com.example.daidaijie.syllabusapplication.ModuleInfo.api.DeletePostApi;
import com.example.daidaijie.syllabusapplication.ModuleInfo.api.ThumbUpApi;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoDeleteReturn;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoHttpBean;
import com.example.daidaijie.syllabusapplication.base.IBaseModel;
import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.bean.PostListBean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUp;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpsBean;
import com.example.daidaijie.syllabusapplication.bean.UserInfo;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.util.GsonUtil;
import com.example.daidaijie.syllabusapplication.util.RetrofitUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class SchoolCircleModel implements ISchoolCircleModel {

    List<PostListBean> mPostListBeen;

    CirclesApi mCirclesApi;

    IUserModel mIUserModel;

    ThumbUpApi mThumbUpApi;

    DeletePostApi mDeletePostApi;

    private int lowID;

    public SchoolCircleModel(CirclesApi circlesApi, IUserModel IUserModel,
                             ThumbUpApi thumbUpApi, DeletePostApi deletePostApi) {
        mCirclesApi = circlesApi;
        mThumbUpApi = thumbUpApi;
        mIUserModel = IUserModel;
        mDeletePostApi = deletePostApi;
        lowID = Integer.MAX_VALUE;
        mPostListBeen = new ArrayList<>();
    }

    @Override
    public Observable<List<PostListBean>> getCircleListFromNet(int topic_id,int page_index) {
        return mCirclesApi.getCircles(topic_id, page_index)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<InfoHttpBean, Observable<List<PostListBean>>>() {
                    @Override
                    public Observable<List<PostListBean>> call(InfoHttpBean infoHttpBean) {
                        List<PostListBean> post_list = infoHttpBean.getData();
                        if(post_list.size()==0)
                            return Observable.just(post_list);
                        for (PostListBean bean : post_list) {
                            for (ThumbUpsBean thumbUpsBean : bean.getThumb_ups()) {
//                                if (thumbUpsBean.getUid() == mIUserModel.getUserBaseBeanNormal().getId()) {
                                if (thumbUpsBean.getUid() == 3) {
                                    bean.isMyLove = true;
                                    break;
                                }
                            }
                        }
                        if (lowID != Integer.MAX_VALUE) {
                            mPostListBeen.addAll(post_list);
                        } else {
                            mPostListBeen = post_list;
                        }
                        lowID = post_list.get(post_list.size() - 1).getId();

                        return Observable.just(mPostListBeen);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PostListBean>> loadCircleListFromNet(int topic_id,int page_index) {
        lowID = Integer.MAX_VALUE;
        return getCircleListFromNet(topic_id,page_index);
    }

    @Override
    public void getCircleByPosition(int position, IBaseModel.OnGetSuccessCallBack<PostListBean> onGetSuccessCallBack) {
        onGetSuccessCallBack.onGetSuccess(mPostListBeen.get(position));
    }

    @Override
    public Observable<ThumbUpReturn> like(int position) {
        final PostListBean postListBean = mPostListBeen.get(position);
//        final ThumbUp thumbUp = new ThumbUp(
//                postListBean.getId(),
//                mIUserModel.getUserInfoNormal().getUser_id(),
//                mIUserModel.getUserInfoNormal().getToken());
        final ThumbUp tempthumbUp = new ThumbUp(
                postListBean.getId(),3,"100002");

//        return mThumbUpApi.like(postListBean.getId(), mIUserModel.getUserInfoNormal().getUser_id(),mIUserModel.getUserInfoNormal().getToken())
        return mThumbUpApi.like(tempthumbUp)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ThumbUpReturn, Observable<ThumbUpReturn>>() {
                    @Override
                    public Observable<ThumbUpReturn> call(ThumbUpReturn thumbUpReturnHttpResult) {
//                        Logger.t("thumpUp like").json(GsonUtil.getDefault().toJson(thumbUpReturnHttpResult));
                            ThumbUpReturn thumbUpReturn = thumbUpReturnHttpResult;
                            ThumbUpsBean bean = new ThumbUpsBean();
                            bean.setUid(tempthumbUp.getUid());
                            bean.setId(thumbUpReturn.getId());
                            postListBean.getThumb_ups().add(bean);
                            postListBean.isMyLove = true;
                            return Observable.just(thumbUpReturn);
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> unlike(int position) {
        final PostListBean postListBean = mPostListBeen.get(position);
        ThumbUpsBean myThumbUpsBean = null;
        for (ThumbUpsBean bean : postListBean.getThumb_ups()) {
//            if (bean.getUid() == mIUserModel.getUserInfoNormal().getUser_id()) {
            if (bean.getUid() == 3) {
                    myThumbUpsBean = bean;
                    break;
                }
            }

        if (myThumbUpsBean == null)
            return Observable.error(new Throwable("you haven't thumbUp this!"));
        final ThumbUpsBean finalMyThumbUpsBean = myThumbUpsBean;
//        return mThumbUpApi.unlike(
//                myThumbUpsBean.getId(),
//                mIUserModel.getUserInfoNormal().getUser_id(),
//                mIUserModel.getUserInfoNormal().getToken())
        return mThumbUpApi.unlike(
                myThumbUpsBean.getId(),
                3,
                "100002")
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<InfoDeleteReturn, Observable<String>>() {
                    @Override
                    public Observable<String> call(InfoDeleteReturn infoDeleteReturn) {
                        Logger.t("thumpUp unlike").json(GsonUtil.getDefault().toJson(infoDeleteReturn));
                        if (infoDeleteReturn.getStatus().equals("deleted")) {
                            postListBean.getThumb_ups().remove(finalMyThumbUpsBean);
                            postListBean.isMyLove = false;
                            return Observable.just(infoDeleteReturn.getStatus());
                        } else {
                            return Observable.error(new Throwable(infoDeleteReturn.getStatus()));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PostListBean>> deletePost(final int position) {
        final PostListBean postListBean = mPostListBeen.get(position);
        UserInfo userInfo = mIUserModel.getUserInfoNormal();

        return mDeletePostApi.deletePost(postListBean.getId(),
                userInfo.getUser_id(), userInfo.getToken())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpResult<Void>, Observable<List<PostListBean>>>() {
                    @Override
                    public Observable<List<PostListBean>> call(HttpResult<Void> voidHttpResult) {
                        if (RetrofitUtil.isSuccessful(voidHttpResult)) {
                            mPostListBeen.remove(position);
                            return Observable.just(mPostListBeen);
                        } else {
                            return Observable.error(new Throwable(voidHttpResult.getMessage()));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

}
