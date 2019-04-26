package com.example.daidaijie.syllabusapplication.threeInfo.info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.threeInfo.bean.InfoBean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/27.
 */

public class InfoItemAdapter extends RecyclerView.Adapter<InfoItemAdapter.ViewHolder> implements View.OnClickListener{
    private List<InfoBean> mData;
    private OnItemClickListener mOnItemClickListener;
    public InfoItemAdapter(List<InfoBean> list) {
        mData = list;
    }

    public void updateData(List<InfoBean> list){
        mData.clear();
        mData = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_info_recycle,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.content.setText(mData.get(position).getContent());
        holder.thumbUpLinearLayout.setOnClickListener(this);
        holder.thumbUpLinearLayout.setTag(position);

        holder.commentLinearLayout.setOnClickListener(this);
        holder.commentLinearLayout.setTag(position);

        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return  mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View view) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public interface OnLikeStateChangeListener {
        void onLike(boolean isLike);

        void onFinish();
    }

    //点赞相关
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        ImageView img1;
        ImageView img2;
        ImageView img3;
        LinearLayout thumbUpLinearLayout;
        TextView zanTextView;
        LinearLayout commentLinearLayout;
        TextView commentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_info_title);
            content = (TextView) itemView.findViewById(R.id.item_info_content);
            img1 = (ImageView) itemView.findViewById(R.id.item_info_img1);
            img2 = (ImageView) itemView.findViewById(R.id.item_info_img2);
            img3 = (ImageView) itemView.findViewById(R.id.item_info_img3);
            thumbUpLinearLayout = (LinearLayout) itemView.findViewById(R.id.thumbUpLinearLayout);
            zanTextView = (TextView) itemView.findViewById(R.id.zanTextView);
            commentLinearLayout =  (LinearLayout) itemView.findViewById(R.id.commentLinearLayout);
            commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
        }
    }
}
