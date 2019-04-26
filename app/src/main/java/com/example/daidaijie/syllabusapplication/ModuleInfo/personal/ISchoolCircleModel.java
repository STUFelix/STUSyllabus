package com.example.daidaijie.syllabusapplication.ModuleInfo.personal;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.CommnetBean;
import com.example.daidaijie.syllabusapplication.base.IBaseModel;
import com.example.daidaijie.syllabusapplication.bean.PostListBean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;

import java.util.List;

import rx.Observable;

/**
 * Created by daidaijie on 2016/10/21.
 */

public interface ISchoolCircleModel {

    Observable<List<PostListBean>> getPostFromNet(int page_index);
    Observable<List<PostListBean>> loadPostFromNet(int page_index);

    Observable<List<CommnetBean>> getCommnetFromNet(int page_index);
    Observable<List<CommnetBean>> loadCommnetFromNet(int page_index);

    void getCircleByPosition(int position, IBaseModel.OnGetSuccessCallBack<PostListBean> onGetSuccessCallBack);

    Observable<ThumbUpReturn> like(int position);

    Observable<String> unlike(int position);

    Observable<List<PostListBean>> deletePost(int position);
    Observable<List<CommnetBean>> deleteComment(int position);

}
