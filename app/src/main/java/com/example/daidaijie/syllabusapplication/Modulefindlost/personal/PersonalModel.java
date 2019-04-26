package com.example.daidaijie.syllabusapplication.Modulefindlost.personal;

import com.example.daidaijie.syllabusapplication.bean.UserInfo;
import com.example.daidaijie.syllabusapplication.Modulefindlost.api.PersonalApi;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.InfoDeleteReturn;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.PersonalHttpBean;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.PersonalPost;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class PersonalModel implements IPersonalModel {

    List<PersonalPost> mCommentListBeen;

    PersonalApi mPersonalApi;

    IUserModel mIUserModel;



    private int lowID;

    public PersonalModel(PersonalApi personalApi, IUserModel IUserModel) {
        mPersonalApi = personalApi;
        mIUserModel = IUserModel;
        lowID = Integer.MAX_VALUE;
        mCommentListBeen = new ArrayList<>();
    }


    @Override
    public Observable<List<PersonalPost>> getPostFromNet(int page_index) {
        int uid = mIUserModel.getUserInfoNormal().getUser_id();
        String token = mIUserModel.getUserInfoNormal().getToken();
        return mPersonalApi.getPersonalPost(3,"100002", page_index)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<PersonalHttpBean, Observable<List<PersonalPost>>>() {
                    @Override
                    public Observable<List<PersonalPost>> call(PersonalHttpBean infoHttpBean) {
                        List<PersonalPost> post_list = infoHttpBean.getData().getFindlost_list();
                        mCommentListBeen.addAll(post_list);

                        return Observable.just(post_list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PersonalPost>> loadPostFromNet(int page_index) {
        lowID = Integer.MAX_VALUE;
        return getPostFromNet(page_index);
    }


    @Override
    public Observable<List<PersonalPost>> deletePost(final int position) {
        final PersonalPost postListBean = mCommentListBeen.get(position);
        UserInfo userInfo = mIUserModel.getUserInfoNormal();
//        userInfo.getUser_id(), userInfo.getToken())
        return mPersonalApi.deletePost(postListBean.getId(),
                3, "100002")
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<InfoDeleteReturn, Observable<List<PersonalPost>>>() {
                    @Override
                    public Observable<List<PersonalPost>> call(InfoDeleteReturn voidHttpResult) {
                        if (voidHttpResult != null) {
                            mCommentListBeen.remove(position);
                            return Observable.just(mCommentListBeen);
                        } else {
                            return Observable.error(new Throwable("ERROR"));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public PersonalPost getFindLostByPosition(int position) {
        return mCommentListBeen.get(position);
    }
}
