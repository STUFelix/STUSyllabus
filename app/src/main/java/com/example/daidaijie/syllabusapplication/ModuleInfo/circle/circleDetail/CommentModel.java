package com.example.daidaijie.syllabusapplication.ModuleInfo.circle.circleDetail;

import com.example.daidaijie.syllabusapplication.base.IBaseModel;
import com.example.daidaijie.syllabusapplication.bean.CommentInfo;
import com.example.daidaijie.syllabusapplication.bean.PostCommentBean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class CommentModel implements ICommentModel {

    int mPostId;

    CircleCommentsApi mCircleCommentsApi;

    List<CommentInfo.CommentsBean> mCommentsBeens;

    IUserModel mIUserModel;

    public CommentModel(CircleCommentsApi circleCommentsApi, IUserModel userModel) {
        mCircleCommentsApi = circleCommentsApi;
        mIUserModel = userModel;
    }

    public void setPostId(int postId) {
        mPostId = postId;
    }

    @Override
    public Observable<List<CommentInfo.CommentsBean>> getCommentsFromNet() {
        return mCircleCommentsApi.getComments(mPostId)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<CommentInfo, Observable<List<CommentInfo.CommentsBean>>>() {
                    @Override
                    public Observable<List<CommentInfo.CommentsBean>> call(CommentInfo commentInfoHttpResult) {
                        if (commentInfoHttpResult.getComments() != null) {
                            mCommentsBeens = commentInfoHttpResult.getComments();
                            return Observable.just(mCommentsBeens);
                        } else{
                            return Observable.just(null);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void getCommentNormal(int position, IBaseModel.OnGetSuccessCallBack<CommentInfo.CommentsBean> getSuccessCallBack) {
        getSuccessCallBack.onGetSuccess(mCommentsBeens.get(position));
    }

    @Override
    public Observable<List<CommentInfo.CommentsBean>> sendCommentToNet(String msg) {
//        PostCommentBean postCommentBean = new PostCommentBean(
//                mPostId,
//                mIUserModel.getUserInfoNormal().getUser_id(),
//                msg,
//                mIUserModel.getUserInfoNormal().getToken()
//        );
        PostCommentBean postCommentBean = new PostCommentBean(
                mPostId,
                3,
                msg,
                "100002"
        );

        return mCircleCommentsApi.sendComment(postCommentBean)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ThumbUpReturn, Observable<List<CommentInfo.CommentsBean>>>() {
                    @Override
                    public Observable<List<CommentInfo.CommentsBean>> call(ThumbUpReturn voidHttpResult) {
                        if (voidHttpResult.getId() != -1) {
                            return getCommentsFromNet();
                        }
                        return Observable.error(new Throwable(voidHttpResult.toString()));
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }
}
