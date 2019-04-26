package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.postContent;

import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;
import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.PersonalPost;

import java.util.List;

/**
 * Created by daidaijie on 2016/10/8.
 */

public interface PostContentContract {

    interface presenter extends BasePresenter {
        void selectPhoto();

        void unSelectPhoto(int position);

        boolean isNonePhoto();

        void postContent(int kind,String title,String desc,String local,String contact);
        void modifyContent(int kind,String title,String desc,String local,String contact,String photo,int findlost_id);

        void getPost(int id);
    }

    interface view extends BaseView<presenter> {

        void showLoading(boolean isShow);

        void setUpFlow(List<String> PhotoImgs);

        void showFailMessage(String msg);

        void showWarningMessage(String msg);

        void onPostFinishCallBack();

        void showData(FindBean personalPost);
    }
}
