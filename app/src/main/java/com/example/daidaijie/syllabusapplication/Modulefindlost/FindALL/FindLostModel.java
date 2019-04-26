package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL;

import com.example.daidaijie.syllabusapplication.Modulefindlost.api.FIndApi;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindHttpBean;
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

public class FindLostModel implements IFindLostModel {

    List<FindBean> mPostListBeen;

    FIndApi mFIndApi;

    IUserModel mIUserModel;


    private int lowID;

    public FindLostModel(FIndApi FIndApi, IUserModel IUserModel) {
        mFIndApi = FIndApi;
        mIUserModel = IUserModel;
        lowID = Integer.MAX_VALUE;
        mPostListBeen = new ArrayList<>();
    }

    @Override
    public Observable<List<FindBean>> getFindLostListFromNet(int kind, int page_index) {
        // 改成uid和token
        return mFIndApi.getFinds(3,"100002",kind, page_index)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<FindHttpBean, Observable<List<FindBean>>>() {
                    @Override
                    public Observable<List<FindBean>> call(FindHttpBean findHttpBean) {
                        List<FindBean> post_list = findHttpBean.getData().getFindlost_list();
                        if(post_list.size()==0)
                            return Observable.just(post_list);

                        mPostListBeen = post_list;
                        return Observable.just(mPostListBeen);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<FindBean>> loadFindLostListFromNet(int kind,int page_index) {
        lowID = Integer.MAX_VALUE;
        return getFindLostListFromNet(kind,page_index);
    }


}
