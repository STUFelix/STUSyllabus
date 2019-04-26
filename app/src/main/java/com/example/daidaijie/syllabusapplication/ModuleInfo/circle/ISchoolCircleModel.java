package com.example.daidaijie.syllabusapplication.ModuleInfo.circle;

import com.example.daidaijie.syllabusapplication.base.IBaseModel;
import com.example.daidaijie.syllabusapplication.bean.PostListBean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;

import java.util.List;

import rx.Observable;

/**
 * Created by daidaijie on 2016/10/21.
 */

public interface ISchoolCircleModel {

    Observable<List<PostListBean>> getCircleListFromNet(int topic_id,int page_index);

    Observable<List<PostListBean>> loadCircleListFromNet(int topic_id,int page_index);

    void getCircleByPosition(int position, IBaseModel.OnGetSuccessCallBack<PostListBean> onGetSuccessCallBack);

    Observable<ThumbUpReturn> like(int position);

    Observable<String> unlike(int position);

    Observable<List<PostListBean>> deletePost(int position);
}
