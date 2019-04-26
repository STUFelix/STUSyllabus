package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.postContent;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;
import com.example.daidaijie.syllabusapplication.bean.PhotoInfo;
import com.example.daidaijie.syllabusapplication.bean.QiNiuImageInfo;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.LostAddBean;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.postBean;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.util.GsonUtil;
import com.example.daidaijie.syllabusapplication.util.ImageUploader;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import id.zelory.compressor.Compressor;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class PostContentModel implements IPostContentModel {

    private List<String> mPhotoImgs;

    private IUserModel mIUserModel;
    private static final String TAG = "PostContentModel";

    PushPostApi pushPostApi;

    public PostContentModel(IUserModel IUserModel, PushPostApi pushPostApi) {
        mIUserModel = IUserModel;
        this.pushPostApi = pushPostApi;
        mPhotoImgs = new ArrayList<>();
    }

    @Override
    public List<String> getPhotoImgs() {
        return mPhotoImgs;
    }

    @Override
    public void postPhotoToBmob(final OnPostPhotoCallBack onPostPhotoCallBack) {
        if (mPhotoImgs.size() == 0) {
            onPostPhotoCallBack.onSuccess(null);
            return;
        }

        //图片上传到bmob
        Observable.from(mPhotoImgs)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, File>() {
                    //将文件路径转换成文件类型
                    @Override
                    public File call(String s) {
                        return new File(s.substring("file://".length(), s.length()));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    PhotoInfo photoInfo = new PhotoInfo();

                    @Override
                    public void onStart() {
                        Log.d(TAG, "onStart: ");
                        super.onStart();
                        //初始化
                        photoInfo.setPhoto_list(new ArrayList<PhotoInfo.PhotoListBean>());
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        //onPostPhotoCallBack.onSuccess(photoListJsonString);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, "onError: "+throwable.getMessage());

                        onPostPhotoCallBack.onFail("图片上传失败!");
                    }

                    @Override
                    public void onNext(File file) {
                        Log.d(TAG, "onNext: ");
                        //将图片上传到bmob
                        final BmobFile bmobFile = new BmobFile(file);
                        bmobFile.uploadblock(new UploadFileListener() {

                            @Override
                            public void done(BmobException e) {
                                Log.d(TAG, "done: ");
                                if (e == null) {
                                    PhotoInfo.PhotoListBean photoListBean = new PhotoInfo.PhotoListBean();
                                    //        LoggerUtil.e(bmobFile.getFileUrl());
                                    photoListBean.setSize_big(bmobFile.getFileUrl());
                                    photoListBean.setSize_small(bmobFile.getFileUrl());
                                    photoInfo.getPhoto_list().add(photoListBean);
                                    if (photoInfo.getPhoto_list().size() == mPhotoImgs.size()) {
                                        //将json post到服务器
                                        String photoListJsonString = GsonUtil.getDefault()
                                                .toJson(photoInfo);
                                        LoggerUtil.e(photoListJsonString);
                                        onPostPhotoCallBack.onSuccess(photoListJsonString);
                                    }
                                } else {
                                    onPostPhotoCallBack.onFail("图片上传失败!");
                                }
                            }

                            @Override
                            public void doneError(int code, String msg) {
                                Log.d(TAG, "doneError: "+msg);
                                onPostPhotoCallBack.onFail("图片上传失败!");
                            }
                        });
                    }
                });

    }

    @Override
    public Observable<Integer> pushContent(@Nullable String photoListJson, int kind,String title,String desc,String local,String contac) {
        postBean postContent = new postBean();
        postContent.setDescription(desc);
        postContent.setKind(kind);
        postContent.setTitle(title);
        postContent.setLocation(local);
        postContent.setContact(contac);
        postContent.setImg_link(photoListJson);
//        postContent.uid = mIUserModel.getUserInfoNormal().getUser_id();
//        postContent.token = mIUserModel.getUserInfoNormal().getToken();
        postContent.setUid(3);
        postContent.setToken("100002");
       
        return pushPostApi.post(3,"100002",kind,title,desc,local,
                contac,photoListJson)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<LostAddBean, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(LostAddBean voidHttpResult) {
                        if (voidHttpResult != null) {
                            return Observable.just(voidHttpResult.getId());
                        }
                        return Observable.error(new Throwable(String.valueOf(voidHttpResult.getId())));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<String> modifyContent(@Nullable String photoListJson, int kind,String title,String desc,String local,String contac,int id) {

        return pushPostApi.modify(3,"100002",kind,title,desc,local,
                contac,photoListJson,id)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpBean, Observable<String>>() {
                    @Override
                    public Observable<String> call(HttpBean voidHttpResult) {
                        if (voidHttpResult != null) {
                            return Observable.just(voidHttpResult.getStatus());
                        }
                        return Observable.error(new Throwable(String.valueOf(voidHttpResult.getStatus())));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<FindBean> getPost(int id) {
        return pushPostApi.getPost(id)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<FindBean, Observable<FindBean>>() {
                    @Override
                    public Observable<FindBean> call(FindBean postBean) {
                        return Observable.just(postBean);
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }
}
