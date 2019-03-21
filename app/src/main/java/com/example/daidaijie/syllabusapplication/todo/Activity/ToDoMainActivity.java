package com.example.daidaijie.syllabusapplication.todo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.todo.Bean.Data;
import com.example.daidaijie.syllabusapplication.todo.Bean.TodoBean;
import com.example.daidaijie.syllabusapplication.todo.Bean.TodoList;
import com.example.daidaijie.syllabusapplication.todo.HttpMethods;
import com.example.daidaijie.syllabusapplication.todo.TodoAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class ToDoMainActivity extends AppCompatActivity {

    private List<TodoList> todoLists = new ArrayList<>();

    final String TAG = "TestMainActivity";

    FloatingActionButton toAddTaskActivity;

    private SwipeRefreshLayout refreshLayout;

    private Subscriber subscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_main);
        ListView listView = (ListView) findViewById(R.id.taskListView);
        toAddTaskActivity = (FloatingActionButton) findViewById(R.id.toAddTaskActivity);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        init();
        TodoAdapter todoAdapter = new TodoAdapter(ToDoMainActivity.this, R.layout.todo_item, todoLists);
        listView.setAdapter(todoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoList todoList = todoLists.get(position);
                Intent intent = new Intent(ToDoMainActivity.this, EditOrDeleteActivity.class);
                intent.putExtra("uid",5387);
                intent.putExtra("id", todoList.getId());
                intent.putExtra("token","945025"); //传token
                intent.putExtra("label", todoList.getLabel());
                intent.putExtra("title", todoList.getTitle());
                intent.putExtra("content", todoList.getContent());
                intent.putExtra("start_time",todoList.getStart_time());
                intent.putExtra("deadline_time", todoList.getDeadline_time());
                intent.putExtra("priority", todoList.getPriority());
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                todoLists.clear();
                init();
            }
        });
        toAddTaskActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(ToDoMainActivity.this,AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        subscriber = new Subscriber<TodoBean>() {
            @Override
            public void onCompleted() {
                refreshLayout.setRefreshing(false);
                Toast.makeText(ToDoMainActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(TodoBean todo) {
                Data data = todo.getData();
                List<TodoList> todoList = data.getTodo_list();
                for (int i = 0; i < todoList.size(); i++) {
                    TodoList todoItem = todoList.get(i);
                    Log.d(TAG, "onNext: " + todoItem.getTitle());
                    todoLists.add(todoItem);
                }
            }
        };
        HttpMethods.getInstance().getAllTask(subscriber,5387, "945025",1,1,10);
    }
}


