package com.example.daidaijie.syllabusapplication.mystu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.main.MainActivity;
import com.example.daidaijie.syllabusapplication.mystu.request.CourseDiscussionRequest;

public class CourseInfoListAdapter extends CourseInfoExpandableTextAdapter<CourseInfoListAdapter.TestHolder> {

    private Context mContext;

    public CourseInfoListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestHolder(LayoutInflater.from(mContext).inflate(R.layout.mystu_courseinfolist_single, parent, false));
    }

    @Override
    public void onBindViewHolder(final TestHolder holder, final int position) {
        super.onBindViewHolder(holder,position);

        int count = (CourseInfoActivity.pageNo-1)*10+position+1;
        holder.head.setText(String.valueOf(count));
        if(CourseDiscussionRequest.getCourseFilesLength(position)!=0){
            holder.course_name.setText(CourseDiscussionRequest.getCourseName(position));
            holder.course_name.setTextColor(Color.parseColor("#0000ff"));
            holder.course_name.setClickable(true);
            holder.course_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(mContext,CourseInfoDownloadActivity.class);
                    intent.putExtra("position",position);
                    mContext.startActivity(intent);
                }
            });
        }else {
            holder.course_name.setText(CourseDiscussionRequest.getCourseName(position));
            holder.course_name.setClickable(false);
        }
        holder.course_name.setText(CourseDiscussionRequest.getCourseName(position));
        holder.people_name.setText(CourseDiscussionRequest.getPeopleName(position));
    }

    @Override
    protected int getExpandableTextViewId() {
        return R.id.tv_content;
    }

    @Override
    protected int getTriggerViewId() {
        return R.id.tv_expand_or_collapse;
    }

    @Override
    protected int getMaxLineCount() {
        return 3;
    }

    @Override
    protected String getItemDataId(int position) {
        return String.valueOf(position);
    }

    @Override
    protected String getExpandText() {
        return "全文";
    }

    @Override
    protected String getCollapseText() {
        return "收起";
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TestHolder extends RecyclerView.ViewHolder {

        public TextView head;

        public TextView course_name;

        public TextView people_name;


        public TestHolder(View itemView) {
            super(itemView);
            head = (TextView) itemView.findViewById(R.id.courseinfo_tv_head);
            course_name = (TextView) itemView.findViewById(R.id.courseinfo_tv_coursename);
            people_name =(TextView) itemView.findViewById(R.id.courseinfo_tv_peoplename);
        }
    }
}