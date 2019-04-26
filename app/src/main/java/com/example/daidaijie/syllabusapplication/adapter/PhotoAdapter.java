package com.example.daidaijie.syllabusapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.bean.PhotoInfo;
import com.example.daidaijie.syllabusapplication.other.PhotoDetailActivity;
import com.example.daidaijie.syllabusapplication.util.DensityUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by daidaijie on 2016/8/9.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {


    private Activity mActivity;

    private PhotoInfo mPhotoInfo;

    private int mWidth;
    private int mHeight = 0;
    private static final String TAG = "PostContent";
    public PhotoAdapter(Activity activity, PhotoInfo photoInfo, int width) {
        Log.d(TAG, "PhotoAdapter: ");
        mActivity = activity;
        mPhotoInfo = photoInfo;
        mWidth = width;
    }
    public PhotoAdapter(Activity activity, PhotoInfo photoInfo, int width,int height) {
        Log.d(TAG, "PhotoAdapter: ");
        mActivity = activity;
        mPhotoInfo = photoInfo;
        mWidth = width;
        mHeight = width;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        //之前显示不出来是因为没设置item_photo的大小
        View view = inflater.inflate(R.layout.item_photo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        final PhotoInfo.PhotoListBean photoBean = mPhotoInfo.getPhoto_list().get(position);
        int width;
        if( mHeight != 0){
            width = mWidth /5 - DensityUtil.dip2px(mActivity,2);
        }
        else
            width = mWidth / getItemCount() - DensityUtil.dip2px(mActivity,2);

        ViewGroup.LayoutParams layoutParams = holder.mPhotoSimpleDraweeView.getLayoutParams();
        layoutParams.width = width;
        if( mHeight != 0){
            layoutParams.height = width;
        }
        holder.mPhotoSimpleDraweeView.setImageURI(photoBean.getSize_small());

        if(mHeight!=0){
            holder.mPhotoSimpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = PhotoDetailActivity.getIntent(mActivity,
                            mPhotoInfo.getBigUrls(), position);
                    mActivity.startActivity(intent);
                }
            });
        }else{
            holder.mPhotoSimpleDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //長按刪除
                    mPhotoInfo.getPhoto_list().remove(position);
                    notifyDataSetChanged();
                    return false;
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return mPhotoInfo.getPhoto_list().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photoSimpleDraweeView)
        SimpleDraweeView mPhotoSimpleDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
