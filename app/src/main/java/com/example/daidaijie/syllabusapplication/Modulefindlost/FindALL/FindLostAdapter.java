package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.adapter.PhotoAdapter;
import com.example.daidaijie.syllabusapplication.bean.PhotoInfo;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;
import com.example.daidaijie.syllabusapplication.util.DensityUtil;
import com.example.daidaijie.syllabusapplication.util.GsonUtil;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by daidaijie on 2016/8/9.
 */
public class FindLostAdapter extends RecyclerView.Adapter<FindLostAdapter.ViewHolder> {

    public static final String TAG = "CirclesAdapter";

    Activity mActivity;

    private List<FindBean> mPostListBeen;

    private int mWidth;

    //判断是列表还是详情，列表是false，详情是true
    private boolean isOnlyOne;


    public static final int MODE_ITEM_CLICK = 0;
    public static final int MODE_TEXT_CLICK = 1;

    public interface OnLongClickCallBack {
        void onLongClick(int position, int mode);
    }

    OnLongClickCallBack mOnLongClickCallBack;

    public OnLongClickCallBack getOnLongClickCallBack() {
        return mOnLongClickCallBack;
    }

    public void setOnLongClickCallBack(OnLongClickCallBack onLongClickCallBack) {
        mOnLongClickCallBack = onLongClickCallBack;
    }

    public FindLostAdapter(Activity activity, List<FindBean> postListBeen) {
        mActivity = activity;
        mPostListBeen = postListBeen;
        isOnlyOne = false;
    }

    public FindLostAdapter(Activity activity, List<FindBean> postListBeen, int width) {
        mActivity = activity;
        mPostListBeen = postListBeen;
        mWidth = width;
        isOnlyOne = true;
    }

    public List<FindBean> getmPostListBeen() {
        return mPostListBeen;
    }

    public void setmPostListBeen(List<FindBean> mPostListBeen) {
        this.mPostListBeen = mPostListBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.item_find, parent, false);

        //没传入就直接计算
        if (mWidth == 0) {
            mWidth = parent.getWidth() - DensityUtil.dip2px(mActivity, 48 + 1) + 2;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FindBean postBean = mPostListBeen.get(position);

        holder.mPostInfoTextView.setText(postBean.getRelease_time());
        holder.titleView.setText(postBean.getTitle());
        holder.localView.setText("地址:"+postBean.getLocation());
        holder.decrView.setText(postBean.getDescription());
        holder.contactView.setText("联系方式:"+postBean.getContact());

        if (postBean.getImg_link() != null && !postBean.getImg_link().isEmpty()
                && !postBean.getImg_link().equals("null")) {
            holder.mPhotoRecyclerView.setVisibility(View.VISIBLE);
            PhotoInfo photoInfo = GsonUtil.getDefault()
                    .fromJson(postBean.getImg_link(), PhotoInfo.class);
            PhotoAdapter photoAdapter = new PhotoAdapter(mActivity, photoInfo,
                    mWidth);
            holder.mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,
                    LinearLayoutManager.HORIZONTAL, false));
            holder.mPhotoRecyclerView.setAdapter(photoAdapter);
        } else {
            holder.mPhotoRecyclerView.setVisibility(View.GONE);
        }


        //长按按钮
        holder.contactView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongClickCallBack != null) {
                    mOnLongClickCallBack.onLongClick(position, MODE_TEXT_CLICK);
                }
                return true;
            }
        });



//        String[] items = {"复制"};
//        AlertDialog dialog = new AlertDialog.Builder(mActivity)
//                .setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == 0) {
//                            ClipboardUtil.copyToClipboard(holder.mContentTextView.getText().toString());
//                        }
//                    }
//                })
//                .create();
//        dialog.show();
    }


    @Override
    public int getItemCount() {
        if (mPostListBeen == null) {
            return 0;
        }
        return mPostListBeen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.postInfoTextView)
        TextView mPostInfoTextView;
        @BindView(R.id.photoRecyclerView)
        RecyclerView mPhotoRecyclerView;
        @BindView(R.id.titleView)
        TextView titleView;
        @BindView(R.id.localView)
        TextView localView;
        @BindView(R.id.decrView)
        TextView decrView;
        @BindView(R.id.contactView)
        TextView contactView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
