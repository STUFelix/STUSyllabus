package com.example.daidaijie.syllabusapplication.mystu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.daidaijie.syllabusapplication.R;


public class CourseWorkAdapter extends CourseWorkExpandableTextAdapter<CourseWorkAdapter.TestHolder> implements View.OnClickListener {

    private Context mContext;

    /*****为Recycle增加监听事件***********/
    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



    public CourseWorkAdapter(Context context) {
        mContext = context;
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mystu_courseworkitem, parent, false);

        view.setOnClickListener(this);

        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(final TestHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        holder.itemView.setTag(position);

        holder.head.setText(String.valueOf(position + 1));

        holder.name.setText(CourseWorkUtil.getName(position));

    }

    @Override
    protected int getExpandableTextViewId() {
        return R.id.coursework_tv_content;
    }

    @Override
    protected int getTriggerViewId() {
        return R.id.coursework_tv_expand_or_collapse;
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
        //一共有多少个Item
        return CourseWorkUtil.getWorklistNum();
    }

    public class TestHolder extends RecyclerView.ViewHolder {

        public TextView head;

        public TextView name;

        public TestHolder(View itemView) {
            super(itemView);
            head = (TextView) itemView.findViewById(R.id.coursework_tv_head);
            name = (TextView) itemView.findViewById(R.id.coursework_tv_name);
        }
    }
}