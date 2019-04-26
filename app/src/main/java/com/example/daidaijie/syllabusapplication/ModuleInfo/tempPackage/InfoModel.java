package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;


import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.Info4Bean;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoApi;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoHttpBean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUp;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpsBean;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 16zhchen on 2018/12/14.
 */

public class InfoModel implements IInfoModel {

    InfoApi mApi;
    IUserModel mIUserModel;
    List<Info4Bean> list;

    public InfoModel(InfoApi api,IUserModel IUserModel){
        mApi = api;
        mIUserModel = IUserModel;
    }

    @Override
    public Observable<List<Info4Bean>> getInfo() {
        return mApi.getAllTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<InfoHttpBean, List<Info4Bean>>() {
                    @Override
                    public List<Info4Bean> call(InfoHttpBean infoHttpBean) {
//                        list = infoHttpBean.getPost_list();
//                        list = infoHttpBean.getData();
//                        for(Info4Bean info4Bean:list){
//
//                            for(ThumbUpsBean thumbUpsBean: info4Bean.getThumb_ups()){
////                                if(thumbUpsBean.getUid()==mIUserModel.getUserInfoNormal().getUser_id())
//                                if(thumbUpsBean.getUid()==3)
//                                    info4Bean.isMylove = true;
//                            }
//                        }

                        return null;
                    }
                });
    }

    @Override
    public Observable<List<Info4Bean>> getOnePageInfo(int pageIndex) {
        return mApi.getOnePageTask(-1,pageIndex).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<InfoHttpBean, List<Info4Bean>>() {
                    @Override
                    public List<Info4Bean> call(InfoHttpBean infoHttpBean) {
//                        list = infoHttpBean.getPost_list();
//                        list = infoHttpBean.getData();
//                        for(Info4Bean info4Bean:list){
//                            for(ThumbUpsBean thumbUpsBean: info4Bean.getThumb_ups()){
////                                if(thumbUpsBean.getUid()==mIUserModel.getUserInfoNormal().getUser_id())
//                                if(thumbUpsBean.getUid()==3)
//                                    info4Bean.isMylove = true;
//                            }
//                        }

                        return null;
                    }
                });
    }

    @Override
    public Observable<ThumbUpReturn> like(final int position) {
        final Info4Bean infobean = list.get(position);
//        final ThumbUp thumbUp = new ThumbUp(
//                infobean.getId(),
//                mIUserModel.getUserInfoNormal().getUser_id(),
//                mIUserModel.getUserInfoNormal().getToken());
        final ThumbUp thumbUp = new ThumbUp(infobean.getId(), 3,"100002");
        return mApi.like(thumbUp)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ThumbUpReturn, Observable<ThumbUpReturn>>() {
                    @Override
                    public Observable<ThumbUpReturn> call(ThumbUpReturn thumbUpReturn) {
                        //点赞添加实例
                        ThumbUpsBean bean = new ThumbUpsBean();
//                        bean.setUid(thumbUp.getUid());
                        bean.setUid(3);
                        bean.setId(thumbUpReturn.getId());
                        infobean.getThumb_ups().add(bean);
                        infobean.isMylove = true;
                        return Observable.just(thumbUpReturn);
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ThumbUpReturn> unlike(int position) {
        final Info4Bean infobean = list.get(position);

        ThumbUpsBean myThumbUpsBean = null;
        for (ThumbUpsBean bean : infobean.getThumb_ups()) {
            if (bean.getUid() == mIUserModel.getUserInfoNormal().getUser_id()) {
                myThumbUpsBean = bean;
                break;
            }
        }
        if (myThumbUpsBean == null)
            return Observable.error(new Throwable("you haven't thumbUp this!"));
        final ThumbUpsBean finalMyThumbUpsBean = myThumbUpsBean;


        return mApi.unlike(infobean.getId(),3,"100002")
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ThumbUpReturn, Observable<ThumbUpReturn>>() {
                    @Override
                    public Observable<ThumbUpReturn> call(ThumbUpReturn thumbUpReturn) {
                        //点赞添加实例
                        infobean.getThumb_ups().remove(finalMyThumbUpsBean);
                        return Observable.just(thumbUpReturn);
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }
}
