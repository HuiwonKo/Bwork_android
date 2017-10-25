package com.perples.recosample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class SchedulerDialog extends Dialog {
    private Button okBtn, closeBtn;
    private Button datebtn, timebtn;
    private EditText planEt, locationEt;
    private String plan, date, timetime, location;
    int year, month, day, hour, minute;
    Context context;

    public SchedulerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduler_dialog);

        GregorianCalendar calendar = new GregorianCalendar();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);


        okBtn = (Button) findViewById(R.id.okBtn);
        closeBtn = (Button) findViewById(R.id.closeBtn);
        planEt = (EditText) findViewById(R.id.planeditText);
        locationEt = (EditText) findViewById(R.id.locationeditText);

        datebtn = (Button) findViewById(R.id.datebtn);
        timebtn = (Button) findViewById(R.id.timebtn);

        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, hour, minute, false);
                timePickerDialog.show();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan = planEt.getText().toString();
                location = locationEt.getText().toString();
                date = datebtn.getText().toString();
                timetime = timebtn.getText().toString();

                dismiss();  //이후 얘가 올라와있는 Activity(AppUsersActivity)에서 구현해준 Dissmiss 리스너가 작동함
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //datebtn.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            int month = monthOfYear + 1;
            String formattedMonth = "" + month;
            String formattedDayOfMonth = "" + dayOfMonth;

            if(month < 10){
                formattedMonth = "0" + month;
            }
            if(dayOfMonth < 10){
                formattedDayOfMonth = "0" + dayOfMonth;
            }
            datebtn.setText(year + "-" + formattedMonth + "-" + formattedDayOfMonth);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //timebtn.setText(hourOfDay + ":" + minute);
            String formattedHourOfDay = "" + hourOfDay;
            String formattedminute = "" + minute;

            if(hourOfDay < 10){
                formattedHourOfDay = "0" + hourOfDay;
            }
            if(minute < 10){
                formattedminute = "0" + minute;
            }
            timebtn.setText(formattedHourOfDay + ":" + formattedminute);
        }
    };

    public void init() {
        datebtn.setText("날짜 선택");
        timebtn.setText("시각 선택");
        planEt.setText(null);
        locationEt.setText(null);
    }

    public String getPlan() {  //일정을 리턴
        return plan;
    }

    public String getTimetime() {  //시를 리턴
        return timetime;
    }

    public String getDate() {  //분을 리턴
        return date;
    }

    public String getLocation() {  //분을 리턴
        return location;
    }
}