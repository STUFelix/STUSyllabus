package com.example.daidaijie.syllabusapplication.Modulefindlost.personal.comments;

import android.app.Activity;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.CommnetBean;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.PersonalPost;

import java.util.List;

/**
 * Created by daidaijie on 2016/10/8.
 */

public interface CommnetsContract {

    interface presenter extends BasePresenter, CommentsAdapter.OnDeleteCallBack,CommentsAdapter.OnModifyCallBack {
        void refresh();

        void loadData();

    }

    interface view extends BaseView<presenter> {

        void showEnsureDeleteDialog(int position);

        void showRefresh(boolean isShow);

        void showLoading(boolean isShow);

        void showFailMessage(String msg);

        void showSuccessMessage(String msg);

        void loadMoreFinish();

        void showData(List<PersonalPost> postListBeen);

        void showContentDialog(CommnetBean postListBean, boolean isShowTitle, boolean isShowDelete, int position);
        int getPage_index();
        void setPage_index(int page_index);
        void stopLoad();

        void modify(int position);
    }
}
