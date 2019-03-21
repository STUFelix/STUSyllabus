package com.example.daidaijie.syllabusapplication.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.todo.Bean.TodoList;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<TodoList> {

    int resourceId;

    public TodoAdapter(Context context, int resource, List<TodoList> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoList todoList = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.firstChar = (TextView) view.findViewById(R.id.firstChar);
            viewHolder.firstChar.setBackgroundResource(R.drawable.todo_textviewradius);
            viewHolder.todoItemTitle = (TextView) view.findViewById(R.id.todoItemTitle);
            viewHolder.todoItemDeadlineTime = (TextView) view.findViewById(R.id.todoItemDeadlineTime);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.firstChar.setText(""+ todoList.getTitle().charAt(0));
        viewHolder.todoItemTitle.setText(todoList.getTitle());
        viewHolder.todoItemDeadlineTime.setText(todoList.getDeadline_time());
        return view;
    }
    class ViewHolder {
        TextView firstChar;
        TextView todoItemTitle;
        TextView todoItemDeadlineTime;
    }
}
