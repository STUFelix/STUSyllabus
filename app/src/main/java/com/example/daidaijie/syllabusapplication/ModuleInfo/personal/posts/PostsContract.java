package com.example.daidaijie.syllabusapplication.ModuleInfo.personal.posts;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.bean.PostListBean;

import java.util.List;

/**
 * Created by daidaijie on 2016/10/8.
 */

public interface PostsContract {

    interface presenter extends BasePresenter, CirclesAdapter.OnLikeCallBack {
        void refresh();

        void loadData();

        void deletePost(int position);

    }

    interface view extends BaseView<presenter> {

        void showEnsureDeleteDialog(int position);

        void showRefresh(boolean isShow);

        void showLoading(boolean isShow);

        void showFailMessage(String msg);

        void showSuccessMessage(String msg);

        void loadMoreFinish();

        void showData(List<PostListBean> postListBeen);

        void showContentDialog(PostListBean postListBean, boolean isShowTitle, boolean isShowDelete, int position);
        int getPage_index();
        void setPage_index(int page_index);
        void stopLoad();
    }
}
