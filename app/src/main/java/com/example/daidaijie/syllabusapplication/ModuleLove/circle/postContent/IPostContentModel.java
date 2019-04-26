package com.example.daidaijie.syllabusapplication.ModuleLove.circle.postContent;

import android.support.annotation.Nullable;

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

    Observable<Integer> pushContent(@Nullable String photoListJson, String content, String source, String title, int topic_id);
}
