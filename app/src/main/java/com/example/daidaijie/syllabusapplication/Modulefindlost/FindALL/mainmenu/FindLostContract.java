package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.mainmenu;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;

import java.util.List;

/**
 * Created by daidaijie on 2016/10/8.
 */

public interface FindLostContract {

    interface presenter extends BasePresenter {
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

        void showData(List<FindBean> postListBeen);

        void showContentDialog(FindBean postListBean, boolean isShowTitle, boolean isShowDelete, int position);

        int getPage_index();
        void setPage_index(int page_index);
        int getTAB();
        void stopLoad();
    }
}
