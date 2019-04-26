package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.mainmenu;


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

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseFragment;
import com.example.daidaijie.syllabusapplication.event.CircleStateChangeEvent;
import com.example.daidaijie.syllabusapplication.event.ToTopEvent;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;
import com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.FindLostAdapter;
import com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.FindLostComponent;
import com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.postContent.PostContentActivity;
import com.example.daidaijie.syllabusapplication.Modulefindlost.personal.PersonalActivity;
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
public class FindLostFragment extends BaseFragment implements FindLostContract.view, SwipeRefreshLayout.OnRefreshListener, Endless.LoadMoreListener,View.OnClickListener {


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

    private FindLostAdapter mFindLostAdapter;

    @Inject
    FindLostPresenter mFindLostPresenter;

    AlertDialog mLoadingDialog;
    private int page_index = 1;
    private int TAB = -1;
    private static final String TAG = "CreateFragmentTest";

    private boolean isVisible;
    private boolean isCreated = false;

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

    public int getTAB() {
        return TAB;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        DaggerFindLostComponent.builder()
                .findLostComponent(FindLostComponent.getInstance(mAppComponent))
                .findLostModule(new FindLostModule(this))
                .build().inject(this);
    }



    @Override
    protected void init(Bundle savedInstanceState) {
        Log.d(TAG, "init: ");
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            this.TAB = getArguments().getInt("position");
        }
        Log.d(TAG, "init: "+TAB);
        mLoadingDialog = LoadingDialogBuiler.getLoadingDialog(mActivity, ThemeUtil.getInstance().colorPrimary);

        mFindLostAdapter = new FindLostAdapter(getActivity(), null);
        //以后一定要记住这句话
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCircleRecyclerView.setAdapter(mFindLostAdapter);
        mCircleRecyclerView.setItemAnimator(new MyItemAnimator());
        mFindLostAdapter.setOnLongClickCallBack(mFindLostPresenter);

        //设置菜单监听
        mPostContentButton.setOnClickListener(this);
        mMyInfoButton.setOnClickListener(this);

        mRefreshStuCircleLayout.setOnRefreshListener(this);
        setupSwipeRefreshLayout(mRefreshStuCircleLayout);

        mLoadMoreView = mActivity.getLayoutInflater().inflate(R.layout.bottom_load_more, null);
        mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        endless = Endless.applyTo(mCircleRecyclerView, mLoadMoreView);
        endless.setLoadMoreListener(this);

        if(isVisible){
            mRefreshStuCircleLayout.post(new Runnable() {
                @Override
                public void run() {
                    mFindLostPresenter.start();
                }
            });
        }
        isCreated = true;
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
        FindLostComponent.destory();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint: ");
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        if(isCreated && isVisibleToUser){
            mRefreshStuCircleLayout.post(new Runnable() {
                @Override
                public void run() {
                    mFindLostPresenter.start();
                }
            });
            isCreated = false;
        }
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
                    mFindLostPresenter.refresh();
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
        mFindLostAdapter.notifyItemChanged(event.position);
    }

    @Override
    public void showEnsureDeleteDialog(final int position) {
        Log.d(TAG, "showEnsureDeleteDialog: ");
//        AlertDialog dialog = new AlertDialog.Builder(mActivity)
//                .setMessage("确认删除?")
//                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                       mFindLostPresenter.deletePost(position);
//                    }
//                })
//                .setPositiveButton("取消", null).create();
//        dialog.show();
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
    public void showData(List<FindBean> postListBeen) {
        Log.d(TAG, "showData: ");
        mFindLostAdapter.setmPostListBeen(postListBeen);
        mFindLostAdapter.notifyDataSetChanged();
    }

    @Override
    public void showContentDialog(final FindBean postListBean, boolean isShowTitle, boolean isShowDelete, final int position) {
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
                            ClipboardUtil.copyToClipboard(postListBean.getContact().toString());
                        } else {
                            showEnsureDeleteDialog(position);
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        mFindLostPresenter.refresh();
    }

    @Override
    public void onLoadMore(int i) {
        Log.d(TAG, "onLoadMore: ");
        mFindLostPresenter.loadData();
    }
    @Override
    public void stopLoad() {
        Log.d(TAG, "stopLoad: ");
        endless.setLoadMoreListener(null);
    }
}
