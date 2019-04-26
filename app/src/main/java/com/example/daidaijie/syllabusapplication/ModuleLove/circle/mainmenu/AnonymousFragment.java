package com.example.daidaijie.syllabusapplication.ModuleLove.circle.mainmenu;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.daidaijie.syllabusapplication.ModuleLove.circle.CirclesAdapter;
import com.example.daidaijie.syllabusapplication.ModuleLove.circle.StuCircleModelComponent;
import com.example.daidaijie.syllabusapplication.ModuleLove.circle.postContent.PostContentActivity;
import com.example.daidaijie.syllabusapplication.ModuleLove.personal.PersonalActivity;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseFragment;
import com.example.daidaijie.syllabusapplication.bean.PostListBean;
import com.example.daidaijie.syllabusapplication.event.CircleStateChangeEvent;
import com.example.daidaijie.syllabusapplication.event.ToTopEvent;
import com.example.daidaijie.syllabusapplication.util.ClipboardUtil;
import com.example.daidaijie.syllabusapplication.util.SnackbarUtil;
import com.example.daidaijie.syllabusapplication.util.ThemeUtil;
import com.example.daidaijie.syllabusapplication.widget.LoadingDialogBuiler;
import com.example.daidaijie.syllabusapplication.widget.MyItemAnimator;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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
public class AnonymousFragment extends BaseFragment implements AnonymousContract.view, SwipeRefreshLayout.OnRefreshListener, Endless.LoadMoreListener,View.OnClickListener {


    @BindView(R.id.circleRecyclerView)
    RecyclerView mCircleRecyclerView;
    @BindView(R.id.refreshStuCircleLayout)
    SwipeRefreshLayout mRefreshStuCircleLayout;
    @BindView(R.id.fab_circle_menu)
    FloatingActionsMenu mFabMenu;
    @BindView(R.id.postContentButton)
    FloatingActionButton mPostContentButton;
    @BindView(R.id.myInfoButton)
    FloatingActionButton mMyInfoButton;

    View mLoadMoreView;

    Endless endless;

    private CirclesAdapter mCirclesAdapter;

    @Inject
    AnonymousPresenter mAnonymousPresenter;

    AlertDialog mLoadingDialog;
    private int page_index = 1;
    private static final String TAG = "AnonymousFragment";

//    public static StuCircleFragment newInstance(int tab) {
//        StuCircleFragment fragment = new StuCircleFragment();
//        Log.d(TAG, "newInstance: "+tab);
//        fragment.setTAB(tab);
//        Log.d(TAG, "newInstance: "+fragment.getTAB());
//        return fragment;
//    }

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        DaggerAnonymousComponent.builder()
                .stuCircleModelComponent(StuCircleModelComponent.getInstance(mAppComponent))
                .anonymousModule(new AnonymousModule(this))
                .build().inject(this);
    }



    @Override
    protected void init(Bundle savedInstanceState) {
        Log.d(TAG, "init: ");
        EventBus.getDefault().register(this);

        mLoadingDialog = LoadingDialogBuiler.getLoadingDialog(mActivity, ThemeUtil.getInstance().colorPrimary);

        mCirclesAdapter = new CirclesAdapter(getActivity(), null);
        //以后一定要记住这句话
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCircleRecyclerView.setAdapter(mCirclesAdapter);
        mCirclesAdapter.setOnLikeCallBack(mAnonymousPresenter);
        mCircleRecyclerView.setItemAnimator(new MyItemAnimator());
        mCirclesAdapter.setOnLongClickCallBack(mAnonymousPresenter);

        //设置菜单监听
        mPostContentButton.setOnClickListener(this);
        mMyInfoButton.setOnClickListener(this);

        mRefreshStuCircleLayout.setOnRefreshListener(this);
        setupSwipeRefreshLayout(mRefreshStuCircleLayout);

        mLoadMoreView = mActivity.getLayoutInflater().inflate(R.layout.bottom_load_more, null);
        mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        endless = Endless.applyTo(mCircleRecyclerView, mLoadMoreView);
        endless.setLoadMoreListener(this);
        mRefreshStuCircleLayout.post(new Runnable() {
            @Override
            public void run() {
                mAnonymousPresenter.start();
            }
        });

    }


    @Override
    protected int getContentView() {
        Log.d(TAG, "getContentView: ");
        return R.layout.fragment_stu_circle;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
        EventBus.getDefault().unregister(this);
        StuCircleModelComponent.destory();
    }



    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        switch (view.getId()){
            case R.id.postContentButton:
                Intent intent = PostContentActivity.getIntent(mActivity);
                mActivity.startActivity(intent);
                break;
            case R.id.myInfoButton:
                Intent intent1 = new Intent(mActivity, PersonalActivity.class);
                mActivity.startActivity(intent1);
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
        Log.d(TAG, "toTop: ");
        mCircleRecyclerView.smoothScrollToPosition(0);
        if (toTopEvent.isRefresh) {
            mCircleRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAnonymousPresenter.refresh();
                }
            }, 100);
        }
        if (toTopEvent.isShowSuccuess) {
            SnackbarUtil.ShortSnackbar(mCircleRecyclerView, "发送成功", SnackbarUtil.Confirm).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void circleStateChange(CircleStateChangeEvent event) {
        Log.d(TAG, "circleStateChange: ");
        mCirclesAdapter.notifyItemChanged(event.position);
    }

    @Override
    public void showEnsureDeleteDialog(final int position) {
        Log.d(TAG, "showEnsureDeleteDialog: ");
        AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setMessage("确认删除?")
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAnonymousPresenter.deletePost(position);
                    }
                })
                .setPositiveButton("取消", null).create();
        dialog.show();
    }

    @Override
    public void showRefresh(boolean isShow) {
        Log.d(TAG, "showRefresh: ");
        mRefreshStuCircleLayout.setRefreshing(isShow);
    }

    @Override
    public void showLoading(boolean isShow) {
        Log.d(TAG, "showLoading: ");
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showFailMessage(String msg) {
        Log.d(TAG, "showFailMessage: ");
        SnackbarUtil.ShortSnackbar(mPostContentButton, msg, SnackbarUtil.Alert).show();
    }

    @Override
    public void showSuccessMessage(String msg) {
        Log.d(TAG, "showSuccessMessage: ");
        SnackbarUtil.ShortSnackbar(mPostContentButton, msg, SnackbarUtil.Confirm).show();
    }

    @Override
    public void loadMoreFinish() {
        Log.d(TAG, "loadMoreFinish: ");
        endless.loadMoreComplete();
    }

    @Override
    public void showData(List<PostListBean> postListBeen) {
        Log.d(TAG, "showData: ");
        mCirclesAdapter.setPostListBeen(postListBeen);
        mCirclesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showContentDialog(final PostListBean postListBean, boolean isShowTitle, boolean isShowDelete, final int position) {
        Log.d(TAG, "showContentDialog: ");
        String[] items;
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
                            ClipboardUtil.copyToClipboard(postListBean.getContent().toString());
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
        Log.d(TAG, "onRefresh: ");
        mAnonymousPresenter.refresh();
    }

    @Override
    public void onLoadMore(int i) {
        Log.d(TAG, "onLoadMore: ");
        mAnonymousPresenter.loadData();
    }
    @Override
    public void stopLoad() {
        Log.d(TAG, "stopLoad: ");
        endless.setLoadMoreListener(null);
    }
}
