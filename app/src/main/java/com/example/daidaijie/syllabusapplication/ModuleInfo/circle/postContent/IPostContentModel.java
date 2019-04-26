package com.example.daidaijie.syllabusapplication.ModuleInfo.circle.postContent;

import android.support.annotation.Nullable;

import com.example.daidaijie.syllabusapplication.ModuleInfo.api.PostBean;

import java.util.List;

import rx.Observable;

/**
 * Created by daidaijie on 2016/10/21.
 */

public interface IPostContentModel {

    interface OnPostPhotoCallBack {

        void onSuccess(String photoJson);

        void onFail(String msg);
    }

    List<String> getPhotoImgs();

    void postPhotoToBmob(OnPostPhotoCallBack onPostPhotoCallBack);

    Observable<PostBean> getPost(int id);

    Observable<Integer> pushContent(@Nullable String photoListJson, String content, String source,String title,int topic_id);

    Observable<String> modifyContent(@Nullable String photoListJson, String content, String source,String title,int topic_id,int id);
}
