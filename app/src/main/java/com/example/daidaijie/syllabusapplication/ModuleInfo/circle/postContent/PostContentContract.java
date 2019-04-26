package com.example.daidaijie.syllabusapplication.ModuleInfo.circle.postContent;

import com.example.daidaijie.syllabusapplication.ModuleInfo.api.PostBean;
import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;

import java.util.List;

/**
 * Created by daidaijie on 2016/10/8.
 */

public interface PostContentContract {

    interface presenter extends BasePresenter {
        void selectPhoto();

        void unSelectPhoto(int position);

        boolean isNonePhoto();

        void postContent(String msg, String source,String title,int topic_id);
        void modifyContent(String msg, String source,String title,int topic_id ,int id,String photo_json);

        void getPost(int id);
    }

    interface view extends BaseView<presenter> {

        void showLoading(boolean isShow);

        void setUpFlow(List<String> PhotoImgs);

        void showFailMessage(String msg);

        void showWarningMessage(String msg);

        void onPostFinishCallBack();

        void showData(PostBean bean);
    }
}
