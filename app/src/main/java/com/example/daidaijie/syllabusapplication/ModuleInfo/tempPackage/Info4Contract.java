package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.Info4Bean;
import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;

import java.util.List;

/**
 * Created by 16zhchen on 2018/12/2.
 */

public class Info4Contract {
    interface presenter extends BasePresenter, Info4Adapter.OnLikeCallBack {
        void refresh();

        void loadData();

        void handlerFAB();

        void deletePost(int position);

    }

    interface view extends BaseView<presenter> {

        void showLoading(boolean isShow);

        void showRefresh(boolean isShow);

        void showFailMessage(String msg);

        void showSuccessMessage(String msg);

        void showInfoMessage(String msg);

        void loadMoreFinish();

        void showData(List<Info4Bean> schoolDymatics);

        void loadEnd();

        void loadStart();

        void toPostDymatic();

        void showContentDialog(Info4Bean schoolDymatic, boolean isShowTitle, boolean isShowDelete, int position);

        void showEnsureDeleteDialog(final int position);

    }
}
