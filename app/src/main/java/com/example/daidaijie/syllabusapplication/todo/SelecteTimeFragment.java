package com.example.daidaijie.syllabusapplication.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.daidaijie.syllabusapplication.R;

import java.util.Calendar;

public class SelecteTimeFragment extends Fragment {
    final String TAG = "fragment";

    private TextView dateText;
    private TextView timeText;
    public String deadlineTime;
    public String startTime;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment_remind, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        startTime = year + "-" + month + "-" + day + " " + hour + ":" + minute;
        dateText = (TextView) getActivity().findViewById(R.id.dateText);
        dateText.setText(year + "-" + month + "-" + day );
        timeText = (TextView) getActivity().findViewById(R.id.timeText);
        timeText.setText(hour + ":" + minute);
        dateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        timeText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showTimePickDlg();
                    return true;
                }
                return false;
            }
        });
        deadlineTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
        Log.d(TAG, "onActivityCreated: " + deadlineTime);
    }
    void showDatePickDlg() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, year, month-1, day);
        datePickerDialog.show();
    }

    void showTimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeText.setText( hourOfDay+ ":" + minute);
            }
        }, hour, minute, true).show();
    }
}
