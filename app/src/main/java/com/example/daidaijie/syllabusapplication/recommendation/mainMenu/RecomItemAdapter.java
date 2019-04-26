package com.example.daidaijie.syllabusapplication.recommendation.mainMenu;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.recommendation.bean.BaseAdapterBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.CourseBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.TeacherBean;

import butterknife.BindView;


public class RecomItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
        public static final int Q1M1 = 0;
        public static final int Q1M2 = 1;
        public static final int Q1M3 = 2;
        public static final int finalResult = 3;


       private BaseAdapterBean mData;
       private OnItemClickListener mOnItemClickListener;
       private static final String TAG = "RecomItemAdapter";

       public RecomItemAdapter(BaseAdapterBean bean) {
              mData = bean;
       }

       public void updateData(BaseAdapterBean data){
              mData = null;
              mData = data;
              notifyDataSetChanged();

       }
       public BaseAdapterBean getmData(){
           return mData;
       }
       @Override
       public int getItemViewType(int position) {
              return mData.getType();
       }

       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
              View view = null;

              switch (viewType){
                     case Q1M1:{
                            view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.item_recom_q1m1,parent,false);
                            Q1M1ViewHolder viewHolder = new Q1M1ViewHolder(view);
                            view.setOnClickListener(this);
                            return viewHolder;
                     }
                     case Q1M2:{
                         view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.item_recom_q1m2,parent,false);
                         Q1M2ViewHolder viewHolder = new Q1M2ViewHolder(view);
                         view.setOnClickListener(this);
                         return viewHolder;
                     }
                     case Q1M3:{
                         view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.item_recom_q1m3,parent,false);
                         Q1M3ViewHolder viewHolder = new Q1M3ViewHolder(view);
                         view.setOnClickListener(this);
                         return viewHolder;
                     }

              }
              return null;
       }

       @Override
       public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
              if(holder instanceof Q1M1ViewHolder){
                     CourseBean course = (CourseBean) mData.getData().get(position);
                     ((Q1M1ViewHolder) holder).courseName.setText(course.getCourse_name());
                     ((Q1M1ViewHolder) holder).courseID.setText(course.getCourse_id());
                     ((Q1M1ViewHolder) holder).courseUnit.setText(course.getDepartment());
                     ((Q1M1ViewHolder) holder).courseCredit.setText(String.valueOf(course.getCredit()));
              }else if(holder instanceof Q1M2ViewHolder){
                  TeacherBean teacher = (TeacherBean) mData.getData().get(position);
                  ((Q1M2ViewHolder) holder).thName.setText(teacher.getName());
                  ((Q1M2ViewHolder) holder).thUnit.setText(teacher.getDepartment());
              }else if(holder instanceof  Q1M3ViewHolder){
                  String name = (String) mData.getData().get(position);
                  ((Q1M3ViewHolder) holder).departName.setText(name);
              }
                holder.itemView.setTag(position);
       }


       @Override
       public int getItemCount() {
              Log.d(TAG, "getItemCount: ");
              return mData == null ? 0 : mData.getData().size();
       }

       @Override
       public void onClick(View view) {
              Log.d(TAG, "onClick: ");
              if(mOnItemClickListener != null){
                     mOnItemClickListener.onItemClick(view,(int)view.getTag());
              }
       }

       public class Q1M1ViewHolder extends RecyclerView.ViewHolder{
              TextView courseID;
              TextView courseName;
              TextView courseCredit;
              TextView courseUnit;

              public Q1M1ViewHolder(View itemView) {
                     super(itemView);
                     courseID = (TextView) itemView.findViewById(R.id.item_recom_courseID);
                     courseName = (TextView) itemView.findViewById(R.id.item_recom_courseName);
                     courseCredit = (TextView) itemView.findViewById(R.id.item_recom_credit);
                     courseUnit = (TextView) itemView.findViewById(R.id.item_recom_Unit);

              }
       }
       public class Q1M2ViewHolder extends RecyclerView.ViewHolder{
           TextView thName;
           TextView thUnit;

           public Q1M2ViewHolder(View itemView) {
               super(itemView);
               thName = (TextView) itemView.findViewById(R.id.item_recom_thName);
               thUnit = (TextView) itemView.findViewById(R.id.item_recom_thUnit);
           }
       }
       public class Q1M3ViewHolder extends RecyclerView.ViewHolder{

           TextView departName;
           public Q1M3ViewHolder(View itemView) {
               super(itemView);
               departName= (TextView) itemView.findViewById(R.id.item_recom_departName);
           }
       }
       public interface OnItemClickListener{
              void onItemClick(View view,int position);
       }
       public void setOnItemClickListener(OnItemClickListener listener){
              this.mOnItemClickListener = listener;
       }
}
