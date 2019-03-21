package com.example.daidaijie.syllabusapplication.todo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.main.MainActivity;
import com.example.daidaijie.syllabusapplication.todo.Bean.TodoBean;
import com.example.daidaijie.syllabusapplication.todo.HttpMethods;


import rx.Subscriber;

public class EditOrDeleteActivity extends AppCompatActivity {

    final String TAG = "EditOrDeleterActivity";
    String baseUrl = "https://stuapps.com/";

    private Subscriber subscriber;
    private EditText taskTitle;
    private EditText contentEdit;
    private Button editTask;
    private Button deleteTask;

    int uid;
    String token;
    int todo_id;
    String label;
    String title;
    String content;
    String start_time;
    String deadline_time;
    int priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_editordelete);
        taskTitle = (EditText) findViewById(R.id.taskTitle);
        contentEdit = (EditText) findViewById(R.id.contentEditText);

        uid = getIntent().getIntExtra("uid",0);
        token = getIntent().getStringExtra("token");
        todo_id = getIntent().getIntExtra("id", 0);
        label = getIntent().getStringExtra("label");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        start_time = getIntent().getStringExtra("start_time");
        deadline_time = getIntent().getStringExtra("deadline_time");
        priority = getIntent().getIntExtra("priority",0);

        Intent intent = getIntent();
        taskTitle.setText(title);
        contentEdit.setText(content);
        editTask = (Button) findViewById(R.id.editTask);
        deleteTask = (Button) findViewById(R.id.deleteTask);
        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask(uid, token, todo_id, label, title, content, start_time, deadline_time, priority);
                Log.d(TAG, "onClick: ");
            }
        });
        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask(uid, token, todo_id);
                Log.d(TAG, "onClick: "+ uid+token+todo_id);
            }
        });
    }

    protected void editTask(int uid, String token, int todo_id, String label, String title, String content, String start_time, String deadline_time, int priority) {

        title = taskTitle.getText().toString();
        content = contentEdit.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(EditOrDeleteActivity.this, "事项标题不能为空", Toast.LENGTH_SHORT).show();
        } else {
            subscriber = new Subscriber<TodoBean>() {
                @Override
                public void onCompleted() {
                    taskTitle.setText("");
                    contentEdit.setText("");
                    Toast.makeText(EditOrDeleteActivity.this, "修改完成", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "onError: " + e.toString());
                }

                @Override
                public void onNext(TodoBean todoBean) {

                }
            };
            HttpMethods.getInstance().editTask(subscriber, uid, token, todo_id, label, title, content, start_time, deadline_time, priority);
        }
    }

    protected void deleteTask(int uid, String token, int todo_id) {

        subscriber = new Subscriber<TodoBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(EditOrDeleteActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditOrDeleteActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(TodoBean todo) {
            }
        };
        HttpMethods.getInstance().deleteTask(subscriber,5387, "945025",todo_id);

    }
}