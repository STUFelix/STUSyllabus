package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.Info4Bean;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.adapter.PhotoAdapter;
import com.example.daidaijie.syllabusapplication.bean.PhotoInfo;
import com.example.daidaijie.syllabusapplication.schoolDymatic.circle.circleDetail.CircleDetailActivity;
import com.example.daidaijie.syllabusapplication.util.DensityUtil;
import com.example.daidaijie.syllabusapplication.util.GsonUtil;
import com.example.daidaijie.syllabusapplication.widget.ThumbUpView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 16zhchen on 2018/12/14.
 */

public class Info4Adapter extends RecyclerView.Adapter<Info4Adapter.ViewHolder> {
    private List<Info4Bean> mInfoList;
    private Activity mActivity;
    //图片大小
    private int mWidth;

    public Info4Adapter(Activity activity, List<Info4Bean> info4Been){
        this.mActivity = activity;
        this.mInfoList = info4Been;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.item_circle, parent, false);
        //计算宽度
        if (mWidth == 0) {
            mWidth = parent.getWidth() - DensityUtil.dip2px(mActivity, 48 + 1) + 2;
        }

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Info4Bean data = mInfoList.get(position);

        //用户头像
//        holder.mHeadImageDraweeView.setImageURI();
        //发布时间
        holder.mPostInfoTextView.setText("发布时间: "+data.getPost_time());
        if(data.getSource().equals("匿名")){
            holder.mNicknameTextView.setText("");
        }else{
            //用户名
            holder.mNicknameTextView.setText(data.getUser().getNickname());
        }
        //显示来源
        holder.mPostDeviceTextView.setText("来自"+data.getSource());
        //内容显示，需要添加限制
        holder.mContentTextView.setText(data.getContent());
        holder.mZanTextView.setText("赞 [" + data.getThumb_ups().size() + "]");
        holder.mCommentTextView.setText("评论 [" + data.getComments().size() + "]");
        mWidth = mWidth > holder.mContentTextView.getWidth() ? mWidth : holder.mContentTextView.getWidth();

        //设置图片
        if (data.getPhoto_list_json() != null && !data.getPhoto_list_json().isEmpty()) {
            holder.mPhotoRecyclerView.setVisibility(View.VISIBLE);
            PhotoInfo photoInfo = GsonUtil.getDefault()
                    .fromJson(data.getPhoto_list_json(), PhotoInfo.class);
            PhotoAdapter photoAdapter = new PhotoAdapter(mActivity, photoInfo,
                    mWidth);
            holder.mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,
                    LinearLayoutManager.HORIZONTAL, false));
            holder.mPhotoRecyclerView.setAdapter(photoAdapter);
        } else {
            holder.mPhotoRecyclerView.setVisibility(View.GONE);
        }

        //点赞
        holder.mThumbUpView.setEnabled(false);
        if(data.isMylove){
            holder.mThumbUpView.setLike();
        }else{
            holder.mThumbUpView.setUnLike();
        }
        //点赞监听
        holder.mThumbUpLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnLikeCallBack !=null){
                    holder.mZanTextView.setTextColor(mActivity.getResources().getColor(R.color.defaultDarkBackgroundSelect));
                    holder.mThumbUpLinearLayout.setEnabled(false);
                    mOnLikeCallBack.onLike(position, !data.isMylove, new OnLikeStateChangeListener() {
                        @Override
                        public void onLike(boolean isLike) {
                            if (isLike) {
                                holder.mThumbUpView.Like();
                            } else {
                                holder.mThumbUpView.UnLike();
                            }
                        }

                        @Override
                        public void onFinish() {
                            holder.mZanTextView.setTextColor(mActivity.getResources().getColor(R.color.defaultShowColor));
                            holder.mZanTextView.setText("赞 [" + data.getThumb_ups().size() + "]");
                            holder.mThumbUpLinearLayout.setEnabled(true);
                        }
                    });
                }
            }
        });
        //点击跳转
        holder.mItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CircleDetailActivity.getIntent(mActivity, position, mWidth);
                mActivity.startActivity(intent);
            }
        });
//        holder.mCommentLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCommentListener.onComment();
//            }
//        });
    }

    //评论回调接口
    public interface OnCommentListener {
        void onComment();
    }
    public OnCommentListener getCommentListener() {
        return mCommentListener;
    }
    public void setCommentListener(OnCommentListener commentListener) {
        mCommentListener = commentListener;
    }
    OnCommentListener mCommentListener;

    //点赞接口
    public interface OnLikeStateChangeListener {
        void onLike(boolean isLike);
        void onFinish();
    }
    public interface OnLikeCallBack {
        void onLike(int position, boolean isLike, OnLikeStateChangeListener onLikeStateChangeListener);
    }
    OnLikeCallBack mOnLikeCallBack;
    public OnLikeCallBack getOnLikeCallBack() {
        return mOnLikeCallBack;
    }
    public void setOnLikeCallBack(OnLikeCallBack onLikeCallBack) {
        mOnLikeCallBack = onLikeCallBack;
    }

    //长按接口
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


    @Override
    public int getItemCount() {
        if(mInfoList!=null)
            return mInfoList.size();
        return 0;
    }

    public void setmInfoList(List<Info4Bean> mInfoList) {
        this.mInfoList = mInfoList;
    }
    public void addmInfoList(List<Info4Bean> mInfoList) {
        this.mInfoList.addAll(mInfoList);
    }
    public List<Info4Bean> getmInfoList() {

        return mInfoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.headImageDraweeView)
        SimpleDraweeView mHeadImageDraweeView;
        @BindView(R.id.nicknameTextView)
        TextView mNicknameTextView;
        @BindView(R.id.postInfoTextView)
        TextView mPostInfoTextView;
        @BindView(R.id.contentTextView)
        TextView mContentTextView;
        @BindView(R.id.photoRecyclerView)
        RecyclerView mPhotoRecyclerView;
        @BindView(R.id.zanTextView)
        TextView mZanTextView;
        @BindView(R.id.commentTextView)
        TextView mCommentTextView;
        @BindView(R.id.postDeviceTextView)
        TextView mPostDeviceTextView;
        @BindView(R.id.thumbUpView)
        ThumbUpView mThumbUpView;
        @BindView(R.id.thumbUpLinearLayout)
        LinearLayout mThumbUpLinearLayout;
        @BindView(R.id.itemCardView)
        CardView mItemCardView;
        @BindView(R.id.commentLinearLayout)
        LinearLayout mCommentLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
