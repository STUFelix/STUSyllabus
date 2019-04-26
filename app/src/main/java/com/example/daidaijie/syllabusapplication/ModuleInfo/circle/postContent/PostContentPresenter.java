package com.example.daidaijie.syllabusapplication.ModuleInfo.circle.postContent;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.ModuleInfo.api.PostBean;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.util.GsonUtil;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.Subscriber;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class PostContentPresenter implements PostContentContract.presenter {

    IPostContentModel mIPostContentModel;

    PostContentContract.view mView;

    @Inject
    @PerActivity
    public PostContentPresenter(IPostContentModel IPostContentModel, PostContentContract.view view) {
        mIPostContentModel = IPostContentModel;
        mView = view;
    }

    @Override
    public void start() {
        mView.setUpFlow(mIPostContentModel.getPhotoImgs());
    }

    @Override
    public void selectPhoto() {
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(PostContentActivity.MAX_IMG_NUM - mIPostContentModel.getPhotoImgs().size())
                .setEnableCamera(false)
                .setEnableEdit(false)
                .setEnableCrop(false)
                .setEnableRotate(true)
                .setEnablePreview(false)
                .setCropReplaceSource(false)//配置裁剪图片时是否替换原始图片，默认不替换
                .setForceCrop(false)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
                .build();

        GalleryFinal.openGalleryMuti(200, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                for (PhotoInfo photoInfo : resultList) {
                    mIPostContentModel.getPhotoImgs().add("file://" + photoInfo.getPhotoPath());
                    mView.setUpFlow(mIPostContentModel.getPhotoImgs());
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                mView.showFailMessage(errorMsg);
            }
        });
    }

    @Override
    public void unSelectPhoto(int position) {
        mIPostContentModel.getPhotoImgs().remove(position);
        mView.setUpFlow(mIPostContentModel.getPhotoImgs());
    }

    @Override
    public boolean isNonePhoto() {
        return mIPostContentModel.getPhotoImgs().size() == 0;
    }

    @Override
    public void postContent(final String msg, final String source, final String title, final int topic_id) {

        if (isNonePhoto() && msg.isEmpty()) {
            mView.showWarningMessage("请输入文字或者选择图片!");
            return;
        }

        mView.showLoading(true);
        mIPostContentModel.postPhotoToBmob(new IPostContentModel.OnPostPhotoCallBack() {
            @Override
            public void onSuccess(String photoJson) {
                mIPostContentModel.pushContent(photoJson, msg, source,title,topic_id)
                        .subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                mView.showLoading(false);
                                mView.onPostFinishCallBack();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.showLoading(false);
                                if (e.getMessage() == null) {
                                    mView.showFailMessage("发送失败");
                                } else {
                                    if (e.getMessage().toUpperCase().equals("UNAUTHORIZED")) {
                                        mView.showFailMessage(App.getContext().getResources().getString(R.string.UNAUTHORIZED));
                                    } else {
                                        mView.showFailMessage(e.getMessage().toUpperCase());
                                    }
                                }
                            }

                            @Override
                            public void onNext(Integer aVoid) {

                            }
                        });
            }

            @Override
            public void onFail(String msg) {
                mView.showFailMessage(msg);
                mView.showLoading(false);
            }
        });
    }

    @Override
    public void modifyContent(final String msg, final String source, final String title, final int topic_id, final int id,final String photo) {
        if (isNonePhoto() && msg.isEmpty()) {
            mView.showWarningMessage("请输入文字或者选择图片!");
            return;
        }

        mView.showLoading(true);
        mIPostContentModel.postPhotoToBmob(new IPostContentModel.OnPostPhotoCallBack() {
            @Override
            public void onSuccess(String photoJson) {
                //合并URL
                if (photo != null && !photo.isEmpty()
                        && !photo.equals("null")) {
                    com.example.daidaijie.syllabusapplication.bean.PhotoInfo photoInfo1 = GsonUtil.getDefault()
                            .fromJson(photoJson, com.example.daidaijie.syllabusapplication.bean.PhotoInfo.class);
                    com.example.daidaijie.syllabusapplication.bean.PhotoInfo photoInfo2 = GsonUtil.getDefault()
                            .fromJson(photo, com.example.daidaijie.syllabusapplication.bean.PhotoInfo.class);
                    photoInfo1.getPhoto_list().addAll(photoInfo2.getPhoto_list());
                    Gson gson = new Gson();
                    photoJson = gson.toJson(photoInfo1);
                }

                mIPostContentModel.modifyContent(photoJson, msg, source,title,topic_id,id)
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                mView.showLoading(false);
                                mView.onPostFinishCallBack();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.showLoading(false);
                                if (e.getMessage() == null) {
                                    mView.showFailMessage("发送失败");
                                } else {
                                    if (e.getMessage().toUpperCase().equals("UNAUTHORIZED")) {
                                        mView.showFailMessage(App.getContext().getResources().getString(R.string.UNAUTHORIZED));
                                    } else {
                                        mView.showFailMessage(e.getMessage().toUpperCase());
                                    }
                                }
                            }

                            @Override
                            public void onNext(String aVoid) {

                            }
                        });
            }

            @Override
            public void onFail(String msg) {
                mView.showFailMessage(msg);
                mView.showLoading(false);
            }
        });
    }

    @Override
    public void getPost(int id) {
        mIPostContentModel.getPost(id)
                .subscribe(new Subscriber<PostBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PostBean postBean) {
                        mView.showData(postBean);
                    }
                });
    }
}
