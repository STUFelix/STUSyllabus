package com.example.daidaijie.syllabusapplication.ModuleInfo.personal.comments;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.CommnetBean;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.util.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by daidaijie on 2016/8/9.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    public static final String TAG = "CirclesAdapter";

    Activity mActivity;

    private List<CommnetBean> mCommentsList;

    private int mWidth;

    //判断是列表还是详情，列表是false，详情是true

    public interface OnDeleteListener{
        void delete();
    }
    public interface OnDeleteCallBack{
        void delete(int position);
    }
    OnDeleteCallBack mOnDeleteCallBack;
    public OnDeleteCallBack getmOnDeleteCallBack() {
        return mOnDeleteCallBack;
    }

    public void setmOnDeleteCallBack(OnDeleteCallBack mOnDeleteCallBack) {
        this.mOnDeleteCallBack = mOnDeleteCallBack;
    }



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

    public CommentsAdapter(Activity activity, List<CommnetBean> postListBeen) {
        mActivity = activity;
        mCommentsList = postListBeen;
    }

    public CommentsAdapter(Activity activity, List<CommnetBean> postListBeen, int width) {
        mActivity = activity;
        mCommentsList = postListBeen;
        mWidth = width;
    }

    public List<CommnetBean> getmCommentsList() {
        return mCommentsList;
    }

    public void setmCommentsList(List<CommnetBean> mCommentsList) {
        this.mCommentsList = mCommentsList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.item_comments, parent, false);

        //没传入就直接计算
        if (mWidth == 0) {
            mWidth = parent.getWidth() - DensityUtil.dip2px(mActivity, 48 + 1) + 2;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CommnetBean postBean = mCommentsList.get(position);

        CommnetBean.UserBean user = postBean.getUser();

        holder.mNicknameTextView.setText(user.getNickname());
        holder.mPostInfoTextView.setText(postBean.getPost_time());
        holder.mContentTextView.setText(postBean.getComment());
        holder.mDeleteCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnDeleteCallBack != null){
                    mOnDeleteCallBack.delete(position);
                }
            }
        });

        mWidth = mWidth > holder.mContentTextView.getWidth() ? mWidth : holder.mContentTextView.getWidth();


        //长按按钮
        holder.mContentTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongClickCallBack != null) {
                    mOnLongClickCallBack.onLongClick(position, MODE_TEXT_CLICK);
                }
                return true;
            }
        });

        holder.mItemCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongClickCallBack != null) {
                    mOnLongClickCallBack.onLongClick(position, MODE_ITEM_CLICK);
                }
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mCommentsList == null) {
            return 0;
        }
        return mCommentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nicknameTextView)
        TextView mNicknameTextView;
        @BindView(R.id.postInfoTextView)
        TextView mPostInfoTextView;
        @BindView(R.id.contentTextView)
        TextView mContentTextView;
        @BindView(R.id.deletecommentLinearLayout)
        LinearLayout mDeleteCommentLayout;
        @BindView(R.id.itemCardView)
        CardView mItemCardView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
