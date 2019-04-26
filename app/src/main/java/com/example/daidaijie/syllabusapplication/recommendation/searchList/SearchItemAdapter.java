package com.example.daidaijie.syllabusapplication.recommendation.searchList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.recommendation.bean.finalResultBean;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/4.
 */

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> implements View.OnClickListener {
    private List<finalResultBean> mData;
    private OnItemClickListener mOnItemClickListener;

    public SearchItemAdapter(List<finalResultBean> list){
        mData = list;
    }

    public void updateData(List<finalResultBean> list){
        mData.clear();
        mData = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recom_final,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        //view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.courseID.setText(mData.get(position).getCourse_id());
        holder.courseName.setText(mData.get(position).getCourse_name());
        holder.Credit.setText(String.valueOf(mData.get(position).getCredit()));
        holder.department.setText(mData.get(position).getDepartment());
        holder.thName.setText(mData.get(position).getName());
        if(mData.get(position).getAve_score()==0)
            holder.score.setText("暂无评分");
        else holder.score.setText(String.valueOf(mData.get(position).getAve_score()));

        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View view) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView courseName;
        TextView courseID;
        TextView Credit;
        TextView department;
        TextView thName;
        TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            courseID = (TextView) itemView.findViewById(R.id.item_recom_final_courseID);
            courseName = (TextView) itemView.findViewById(R.id.item_recom_final_courseName);
            Credit = (TextView) itemView.findViewById(R.id.item_recom_final_credit);
            department = (TextView) itemView.findViewById(R.id.item_recom_final_Unit);
            thName = (TextView) itemView.findViewById(R.id.item_recom_final_teacher);
            score = (TextView) itemView.findViewById(R.id.item_recom_final_score);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
