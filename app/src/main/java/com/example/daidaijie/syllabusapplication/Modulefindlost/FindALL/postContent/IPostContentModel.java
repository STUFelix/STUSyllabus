package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.postContent;

import android.support.annotation.Nullable;

import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;

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

    Observable<FindBean> getPost(int id);
    Observable<Integer> pushContent(@Nullable String photoListJson, int kind,String title,String desc,String local,String contac);
    Observable<String> modifyContent(@Nullable String photoListJson, int kind,String title,String desc,String local,String contac,int id);
}
