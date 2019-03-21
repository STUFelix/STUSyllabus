package com.example.daidaijie.syllabusapplication.todo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.main.MainActivity;
import com.example.daidaijie.syllabusapplication.todo.Bean.TodoBean;
import com.example.daidaijie.syllabusapplication.todo.HttpMethods;
import com.example.daidaijie.syllabusapplication.todo.SelecteTimeFragment;

import rx.Subscriber;

public class AddTaskActivity extends AppCompatActivity {


    private String title;
    private String des;
    private String startTime;
    private String deadlineTime;
    private Subscriber subscriber;
    private EditText taskTitle;
    private EditText contentEdit;
    private Button sureAddTask;
    private Button cancelAddTask;
    private SwitchCompat toDoHasDateSwitchCompat;
    private SelecteTimeFragment fragment = new SelecteTimeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_add);
        taskTitle = (EditText) findViewById(R.id.taskTitle);
        contentEdit = (EditText) findViewById(R.id.contentEditText);
        sureAddTask = (Button) findViewById(R.id.sureAddTask);
        cancelAddTask = (Button) findViewById(R.id.cancelAddTask);
        toDoHasDateSwitchCompat = (SwitchCompat) findViewById(R.id.toDoHasDateSwitchCompat);
        sureAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureAddTask();
            }
        });
        cancelAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAddTask();
            }
        });
        toDoHasDateSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.selectTime, fragment);
                    transaction.commit();
                }
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.hide(fragment);
                    transaction.commit();
                }
            }
        });
    }

    public void sureAddTask() {
        title = taskTitle.getText().toString();
        des = contentEdit.getText().toString();
        startTime = fragment.startTime;
        deadlineTime = fragment.deadlineTime;
        Log.d(" ", "sureAddTask: " + deadlineTime + startTime);
        if (title == "") {
            Toast.makeText(AddTaskActivity.this, "事项标题不能为空", Toast.LENGTH_SHORT).show();
        } else {
            subscriber = new Subscriber<TodoBean>() {
                @Override
                public void onCompleted() {
                    Toast.makeText(AddTaskActivity.this, "已添加", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(TodoBean todo) {
                }
            };
            HttpMethods.getInstance().addNewTask(subscriber,5387, "945025","",title, des, startTime,deadlineTime,1);
        }
    }

    public void cancelAddTask() {
        finish();
    }

}
