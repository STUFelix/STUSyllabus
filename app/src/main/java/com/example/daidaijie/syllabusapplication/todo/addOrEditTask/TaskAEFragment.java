package com.example.daidaijie.syllabusapplication.todo.addOrEditTask;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseFragment;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TodoRetrofit;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBean;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by 16zhchen on 2018/9/16.
 */

public class TaskAEFragment extends BaseFragment implements TaskAEContract.View, View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "TaskAEFragment";
    @Inject
    TaskAEPresenter mPresenter;

    @BindView(R.id.add_task_title)
    EditText mTitle;
    @BindView(R.id.add_task_content)
    EditText mContent;
    @BindView(R.id.add_task_alarm_switch)
    Switch mAlarmState;
    @BindView(R.id.add_task_alarm_date)
    TextView mAlarmDate;
    @BindView(R.id.add_task_alarm_time)
    TextView mAlarmTime;
    @BindView(R.id.add_task_liner_alarm)
    LinearLayout mAlarmLayout;
    @BindView(R.id.add_task_status_spinner)
    Spinner state;
    @BindView(R.id.add_task_status_textView)
    TextView stateTextView;

    private Calendar c;
    private Long taskType = null;
    private ArrayAdapter<String> adapter;

    public TaskAEFragment(){
    }

    public static TaskAEFragment newInstance(){
        return new TaskAEFragment();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTask(Long id) {
        state.setVisibility(View.VISIBLE);
        stateTextView.setVisibility(View.VISIBLE);
        this.taskType = id;
        TaskBean task  = mPresenter.findTask(id);
        setTitle(task.getTitle());
        setContent(task.getContext());
        state.setSelection(task.getStatus());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(task.getIsAlarm()){
            mAlarmState.setChecked(true);
            mAlarmDate.setVisibility(View.VISIBLE);
            mAlarmTime.setVisibility(View.VISIBLE);
            mAlarmLayout.setVisibility(View.VISIBLE);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            mAlarmDate.setText(sdf.format(task.getTime()));
            sdf = new SimpleDateFormat("HH:mm");
            mAlarmTime.setText(sdf.format(task.getTime()));
        }
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setContent(String content) {
        mContent.setText(content);
    }

    @Override
    public void closePage() {
        getActivity().finish();
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        Log.d(TAG, "init: in");
        createSpinnerAdapter();
        state.setAdapter(adapter);
        stateTextView.setVisibility(View.GONE);
        state.setVisibility(View.GONE);

        c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(now);
        sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(now);

        mAlarmState.setChecked(false);
        mAlarmDate.setVisibility(View.GONE);
        mAlarmTime.setVisibility(View.GONE);
        mAlarmLayout.setVisibility(View.GONE);
        mAlarmDate.setOnClickListener(this);
        mAlarmDate.setText(date);
        mAlarmTime.setOnClickListener(this);
        mAlarmTime.setText(time);
        mAlarmState.setOnCheckedChangeListener(this);

        DaggerTaskAEComponent.builder()
                .taskAEModule(new TaskAEModule(this))
                .userComponent(UserComponent.buildInstance(mAppComponent))
                .build().inject(this);
        mPresenter.setTaskType(taskType);
        mPresenter.start();

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.taskae_fabDone);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: DataManager");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date alarm=null;
                if(mAlarmState.isChecked()){
                    try {
                        alarm = sdf.parse(mAlarmDate.getText().toString()+" "+mAlarmTime.getText().toString()) ;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if(mAlarmState.isChecked())
                    alarmSet();
                if(taskType == 0){
                    //新建存储
                    mPresenter.saveTaskForNew(
                            mTitle.getText().toString(),mContent.getText().toString(),mAlarmState.isChecked(),alarm);
                }else{
                        //编辑存储
                    mPresenter.saveTaskForEdit(
                            mTitle.getText().toString(),mContent.getText().toString(),state.getSelectedItemPosition(),mAlarmState.isChecked(),alarm);
                }

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            //需要提醒
            mAlarmDate.setVisibility(View.VISIBLE);
            mAlarmTime.setVisibility(View.VISIBLE);
            mAlarmLayout.setVisibility(View.VISIBLE);

        }else{
            mAlarmDate.setVisibility(View.GONE);
            mAlarmTime.setVisibility(View.GONE);
            mAlarmLayout.setVisibility(View.GONE);
            alarmCannel();
        }
    }

    @Override
    public void onClick(final View text) {
        if(taskType!=0 ){
            //编辑模式
            TaskBean task  = mPresenter.findTask(taskType);
            if(task.getIsAlarm())
                c.setTime(task.getTime());
            switch (text.getId()){
                case R.id.add_task_alarm_date:
                    showDatePicker(text);
                    break;
                case R.id.add_task_alarm_time:
                    showTimePiker(text);
                    break;
            }
        }else{
            switch (text.getId()){
                case R.id.add_task_alarm_date:
                    showDatePicker(text);
                    break;
                case R.id.add_task_alarm_time:
                    showTimePiker(text);
                    break;
            }
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_task_ae;
    }


    private void alarmSet(){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent;
        Calendar c = Calendar.getInstance();
        Date alarm = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            alarm = sdf.parse(mAlarmDate.getText().toString()+" "+mAlarmTime.getText().toString()) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.setTime(alarm);
        long time = c.getTimeInMillis();
        Intent intent = new Intent(getContext(),AlarmAction.class);
        if(taskType != 0){
            intent.putExtra("taskID",taskType);
        }else{
            intent.putExtra("taskID",mPresenter.getTaskNum()+1);
        }
        pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), pendingIntent);

    }
    private void alarmCannel(){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent;
        Calendar c = Calendar.getInstance();
        Date alarm = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            alarm = sdf.parse(mAlarmDate.getText().toString()+" "+mAlarmTime.getText().toString()) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.setTime(alarm);
        long time = c.getTimeInMillis();
        Intent intent = new Intent(getContext(),AlarmAction.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        alarmManager.cancel(pendingIntent);
    }

    private void showDatePicker(final View text){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TextView t = (TextView) text;
                t.setText(year+"-"+(++month)+"-"+dayOfMonth);
            }
        };
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT,listener,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    private void showTimePiker(final View text){
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                TextView t = (TextView) text;
                t.setText(hourOfDay+":"+minute);
            }
        };
        TimePickerDialog dialog;
        dialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT,listener,
                c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
        dialog.show();
    }
    private void createSpinnerAdapter(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("进行中");
        list.add("已完成");
        adapter = new ArrayAdapter<String>(getContext(),R.layout.item_status_spinner,list);
        adapter.setDropDownViewResource(R.layout.item_status_spinner_drop);

    }
    public void setTaskType(long taskType) {
        this.taskType = taskType;

    }

}
