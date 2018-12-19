package com.example.daidaijie.syllabusapplication.schoolDymatic.personal;

import android.support.annotation.Nullable;

import com.example.daidaijie.syllabusapplication.bean.BmobPhoto;
import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.bean.QiNiuImageInfo;
import com.example.daidaijie.syllabusapplication.bean.UpdateUserBody;
import com.example.daidaijie.syllabusapplication.retrofitApi.PushPostApi;
import com.example.daidaijie.syllabusapplication.retrofitApi.UpdateUserApi;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.util.ImageUploader;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;
import com.example.daidaijie.syllabusapplication.util.RetrofitUtil;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.MediaType;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/22.
 */

public class PersonalModel implements IPersonalModel {

    UpdateUserApi mUpdateUserApi;

    IUserModel mIUserModel;

    PushPostApi pushPostApi;

    public PersonalModel(UpdateUserApi updateUserApi, PushPostApi pushPostApi, IUserModel userModel) {
        mUpdateUserApi = updateUserApi;
        this.pushPostApi = pushPostApi;
        mIUserModel = userModel;
    }


    @Override
    public Observable<Void> updateUserInfo(String nickName, String profile, @Nullable String img) {
        UpdateUserBody updateUserBody = new UpdateUserBody();
        updateUserBody.id = mIUserModel.getUserInfoNormal().getUser_id();
        updateUserBody.uid = mIUserModel.getUserInfoNormal().getUser_id();
        updateUserBody.token = mIUserModel.getUserInfoNormal().getToken();
        updateUserBody.nickname = nickName;
        updateUserBody.profile = profile;
        if (img != null) {
            updateUserBody.image = img;
        }


        return mUpdateUserApi.update(updateUserBody)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpResult<Void>, Observable<Void>>() {
                    @Override
                    public Observable<Void> call(HttpResult<Void> voidHttpResult) {
                        if (RetrofitUtil.isSuccessful(voidHttpResult)) {
                            return Observable.just(voidHttpResult.getData());
                        } else {
                            return Observable.error(new Throwable(voidHttpResult.getMessage()));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void postPhotoToBmob(@Nullable String headImage, final OnPostPhotoCallBack onPostPhotoCallBack) {
        //如果没有选择头像，那么直接返回
        if (headImage == null) {
            onPostPhotoCallBack.onSuccess(null);
            return;
        }

        //将头像传到bmob
        Observable.just(headImage)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, File>() {
                    @Override
                    public File call(String s) {
                        return new File(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {

                    String url;

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.e("aaaaa");
                        onPostPhotoCallBack.onFail("图片上传失败");
                    }

                    @Override
                    public void onNext(File file) {
                        final BmobFile bmobFile = new BmobFile(file);
                        bmobFile.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    url = bmobFile.getFileUrl();
                                    onPostPhotoCallBack.onSuccess(url);
                                }
                            }
                        });
                    }
                });

    }
}
