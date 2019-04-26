package com.example.daidaijie.syllabusapplication.ModuleInfo.personal.comments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.CommnetBean;
import com.example.daidaijie.syllabusapplication.ModuleInfo.circle.postContent.PostContentActivity;
import com.example.daidaijie.syllabusapplication.ModuleInfo.personal.StuCircleModelComponent;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseFragment;
import com.example.daidaijie.syllabusapplication.event.CircleStateChangeEvent;
import com.example.daidaijie.syllabusapplication.event.ToTopEvent;
import com.example.daidaijie.syllabusapplication.util.ClipboardUtil;
import com.example.daidaijie.syllabusapplication.util.SnackbarUtil;
import com.example.daidaijie.syllabusapplication.util.ThemeUtil;
import com.example.daidaijie.syllabusapplication.widget.LoadingDialogBuiler;
import com.example.daidaijie.syllabusapplication.widget.MyItemAnimator;
import com.github.ybq.endless.Endless;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommnetsFragment extends BaseFragment implements CommnetsContract.view, SwipeRefreshLayout.OnRefreshListener, Endless.LoadMoreListener,View.OnClickListener {


    @BindView(R.id.circleRecyclerView)
    RecyclerView mCircleRecyclerView;
    @BindView(R.id.refreshStuCircleLayout)
    SwipeRefreshLayout mRefreshStuCircleLayout;

    View mLoadMoreView;

    Endless endless;

    private CommentsAdapter mCommentsAdapter;

    @Inject
    CommnetsPresenter mCommnetsPresenter;

    AlertDialog mLoadingDialog;
    private int page_index = 1;

    public static CommnetsFragment newInstance() {
        CommnetsFragment fragment = new CommnetsFragment();
        return fragment;
    }

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCommnetsComponent.builder()
                .stuCircleModelComponent(StuCircleModelComponent.getInstance(mAppComponent))
                .commnetsModule(new CommnetsModule(this))
                .build().inject(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        mLoadingDialog = LoadingDialogBuiler.getLoadingDialog(mActivity, ThemeUtil.getInstance().colorPrimary);

        mCommentsAdapter = new CommentsAdapter(getActivity(), null);
        //以后一定要记住这句话
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCircleRecyclerView.setAdapter(mCommentsAdapter);
        mCommentsAdapter.setmOnDeleteCallBack(mCommnetsPresenter);
        mCircleRecyclerView.setItemAnimator(new MyItemAnimator());
        mCommentsAdapter.setOnLongClickCallBack(mCommnetsPresenter);

        mRefreshStuCircleLayout.setOnRefreshListener(this);
        setupSwipeRefreshLayout(mRefreshStuCircleLayout);

        mLoadMoreView = mActivity.getLayoutInflater().inflate(R.layout.bottom_load_more, null);
        mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        endless = Endless.applyTo(mCircleRecyclerView, mLoadMoreView);
        endless.setLoadMoreListener(this);

        mRefreshStuCircleLayout.post(new Runnable() {
            @Override
            public void run() {
                mCommnetsPresenter.start();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_posts;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
        StuCircleModelComponent.destory();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.postContentButton:
                Intent intent = PostContentActivity.getIntent(mActivity);
                mActivity.startActivity(intent);
                break;
        }
    }

//    @OnClick(R.id.postContentButton)
//    public void onClick() {
//        Intent intent = PostContentActivity.getIntent(mActivity);
//        mActivity.startActivity(intent);
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toTop(ToTopEvent toTopEvent) {
        mCircleRecyclerView.smoothScrollToPosition(0);
        if (toTopEvent.isRefresh) {
            mCircleRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCommnetsPresenter.refresh();
                }
            }, 100);
        }
        if (toTopEvent.isShowSuccuess) {
            SnackbarUtil.ShortSnackbar(mCircleRecyclerView, "发送成功", SnackbarUtil.Confirm).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void circleStateChange(CircleStateChangeEvent event) {
        mCommentsAdapter.notifyItemChanged(event.position);
    }

    @Override
    public void showEnsureDeleteDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setMessage("确认删除?")
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCommnetsPresenter.delete(position);
                    }
                })
                .setPositiveButton("取消", null).create();
        dialog.show();
    }

    @Override
    public void showRefresh(boolean isShow) {
        mRefreshStuCircleLayout.setRefreshing(isShow);
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showFailMessage(String msg) {
//        SnackbarUtil.ShortSnackbar(mPostContentButton, msg, SnackbarUtil.Alert).show();
    }

    @Override
    public void showSuccessMessage(String msg) {
//        SnackbarUtil.ShortSnackbar(mPostContentButton, msg, SnackbarUtil.Confirm).show();
    }

    @Override
    public void loadMoreFinish() {
        endless.loadMoreComplete();
    }

    @Override
    public void showData(List<CommnetBean> postListBeen) {
        mCommentsAdapter.setmCommentsList(postListBeen);
        mCommentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showContentDialog(final CommnetBean postListBean, boolean isShowTitle, boolean isShowDelete, final int position) {
        String[] items;
        //查看自己的内容
        isShowDelete = true;

        if (isShowDelete) {
            items = new String[]{"复制", "删除"};
        } else {
            items = new String[]{"复制"};
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            ClipboardUtil.copyToClipboard(postListBean.getComment().toString());
                        } else {
                            showEnsureDeleteDialog(position);
                        }
                    }
                });
        if (isShowTitle) {
            builder.setTitle(postListBean.getUser().getAccount());
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRefresh() {
        mCommnetsPresenter.refresh();
    }

    @Override
    public void onLoadMore(int i) {
        mCommnetsPresenter.loadData();
    }

    @Override
    public void stopLoad() {
        endless.setLoadMoreListener(null);
    }
}
