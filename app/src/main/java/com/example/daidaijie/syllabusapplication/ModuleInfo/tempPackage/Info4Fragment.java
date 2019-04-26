package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.Info4Bean;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseFragment;
import com.example.daidaijie.syllabusapplication.user.UserComponent;
import com.example.daidaijie.syllabusapplication.util.ClipboardUtil;
import com.example.daidaijie.syllabusapplication.util.ThemeUtil;
import com.example.daidaijie.syllabusapplication.widget.LoadingDialogBuiler;
import com.example.daidaijie.syllabusapplication.widget.MyItemAnimator;
import com.github.ybq.endless.Endless;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 16zhchen on 2018/12/2.
 */

public class Info4Fragment extends BaseFragment implements  Info4Contract.view,SwipeRefreshLayout.OnRefreshListener, Endless.LoadMoreListener{
    @BindView(R.id.circleRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshStuCircleLayout)
    SwipeRefreshLayout mRefreshStuCircleLayout;
    @BindView(R.id.postContentButton)
    FloatingActionButton mPostContentButton;
    private static final String TAG = "Info4Fragment";
    private Info4Adapter mAdapter;
    @Inject
    Info4Presenter mPresenter;
    AlertDialog mLoadingDialog;
    View mLoadMoreView;
    Endless endless;
    private int page_index = 1;

    public static Info4Fragment newInstance(){
        Info4Fragment fragment = new Info4Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Presenter绑定
        DaggerInfo4Componnent.builder()
                .userComponent(UserComponent.buildInstance(mAppComponent))
                .info4Module(new Info4Module(this))
                .build().inject(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //显示进度
        mLoadingDialog = LoadingDialogBuiler.getLoadingDialog(mActivity, ThemeUtil.getInstance().colorPrimary);

        mAdapter = new Info4Adapter(getActivity(),null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLikeCallBack(mPresenter);
        mAdapter.setOnLongClickCallBack(mPresenter);
        mAdapter.setCommentListener(new Info4Adapter.OnCommentListener() {
            @Override
            public void onComment() {
                Toast.makeText(getActivity(),"评论",Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setItemAnimator(new MyItemAnimator());

        //下拉刷新
        mRefreshStuCircleLayout.setOnRefreshListener(this);
        setupSwipeRefreshLayout(mRefreshStuCircleLayout);

        mLoadMoreView = mActivity.getLayoutInflater().inflate(R.layout.bottom_load_more, null);
        mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        endless = Endless.applyTo(mRecyclerView, mLoadMoreView);
        endless.setLoadMoreListener(this);
//        mPresenter.loadData();
        mPresenter.loadOnePage(page_index++);
        mRefreshStuCircleLayout.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.start();
            }
        });
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        page_index=1;
        showRefresh(true);
        mPresenter.loadOnePage(page_index++);
        showRefresh(false);
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
    public void showRefresh(boolean isShow) {
        Log.d(TAG, "showRefresh: ");
        mRefreshStuCircleLayout.setRefreshing(isShow);

    }

    @Override
    public void showFailMessage(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showFailMessage: ");
    }

    @Override
    public void showSuccessMessage(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showSuccessMessage: ");
    }

    @Override
    public void showInfoMessage(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showInfoMessage: ");
    }

    @Override
    public void loadMoreFinish() {
        Log.d(TAG, "loadMoreFinish: ");
        
    }

    @Override
    public void showData(List<Info4Bean> schoolDymatics) {
        Log.d(TAG, "showData: ");
        if(page_index == 2) {
            mAdapter.setmInfoList(schoolDymatics);
        }else{
            mAdapter.addmInfoList(schoolDymatics);}
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadEnd() {
        Log.d(TAG, "loadEnd: ");
    }

    @Override
    public void loadStart() {
        Log.d(TAG, "loadStart: ");
    }

    @Override
    public void toPostDymatic() {
        Log.d(TAG, "toPostDymatic: ");
    }

    @Override
    public void showContentDialog(final Info4Bean schoolDymatic, boolean isShowTitle, boolean isShowDelete, final int position) {
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
                            ClipboardUtil.copyToClipboard(schoolDymatic.getContent().toString());
                        } else {
                            showEnsureDeleteDialog(position);
                        }
                    }
                });
        if (isShowTitle) {
            builder.setTitle(schoolDymatic.getUser().getAccount());
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showEnsureDeleteDialog(int position) {
        Log.d(TAG, "showEnsureDeleteDialog: ");
    }

    @Override
    public void onLoadMore(int i) {
        //处理加载
        mPresenter.loadOnePage(page_index++);
        endless.loadMoreComplete();
        Log.d(TAG, "onLoadMore: ");
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_stu_circle;
    }

    @OnClick(R.id.postContentButton)
    public void onClick() {
//        Intent intent = PostContentActivity.getIntent(mActivity);
//        mActivity.startActivity(intent);
        //添加按钮的点击事件
        Toast.makeText(getContext(),"我的",Toast.LENGTH_SHORT).show();
    }
}
