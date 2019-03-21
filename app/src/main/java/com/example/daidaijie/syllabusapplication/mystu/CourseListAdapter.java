package com.example.daidaijie.syllabusapplication.mystu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.daidaijie.syllabusapplication.R;

import java.util.List;
import java.util.Map;

public class CourseListAdapter extends BaseAdapter
{
    private Context context;
    private List<Map<String,String>> list;
    private ViewHolder holder;
    private ClickListener clickListener;

    public CourseListAdapter(Context context, List<Map<String,String>> list){

        super();
        this.context = context;
        this.list = list;
    }
    public  interface ClickListener
    {
        void onclick(String view_name,int postion, String courseLink , String courseLinkId);
    }

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public int getCount()
    {
        return list.size();//报错，list不能为空
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.mystu_courselistsingle, null);

            holder = new ViewHolder();
            holder.course_name = (TextView) convertView.findViewById(R.id.course_name_textView);
            holder.course_resources = (Button) convertView.findViewById(R.id.course_resources_button);
            holder.homework = (Button) convertView.findViewById(R.id.course_homework_button);
            holder.imageView =(ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        //实例化

        holder.course_name.setText(list.get(position).get("course_name"));
        //通过setTag方法来判断对应的功能
        holder.course_resources.setTag("course_resources");
        holder.homework.setTag("course_work");

        holder.course_resources.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickListener.onclick((String) holder.course_resources.getTag(),position
                        ,list.get(position).get("courseLink"),list.get(position).get("courseLinkId"));
            }
        });//为下载课件按钮利用接口回调设置监听事件

        holder.homework.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickListener.onclick((String) holder.homework.getTag(),position
                        ,list.get(position).get("courseLink"),list.get(position).get("courseLinkId"));
            }
        });//为查看作业按钮利用接口回调设置监听事件


        View view = convertView;
        return view;

    }

    class ViewHolder
    {
        TextView course_name;
        Button course_resources,homework;
        ImageView imageView;
    }

}