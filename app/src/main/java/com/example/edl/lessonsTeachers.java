package com.example.edl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class lessonsTeachers extends AppCompatActivity {

    TextView tvTime;
    Calendar ca;
    int Nhour, Nminute;
    String fo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_teachers);
        tvTime= (TextView) findViewById(R.id.tvTime);

        ca= Calendar.getInstance();

        Nhour=ca.get(Calendar.HOUR_OF_DAY);
        Nminute=ca.get(Calendar.MINUTE);

        sTF(Nhour);
        tvTime.setText(Nhour+ " : "+Nminute+ " "+fo );
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(lessonsTeachers.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int Nhour, int Nminute) {
                        sTF(Nhour);
                        tvTime.setText(Nhour+ " : "+Nminute+ " "+fo );
                    }
                }, Nhour, Nminute, true);
                timePickerDialog.show();;
            }
        });

    }
    public void sTF(int Nhour){
        if (Nhour==0) {
            Nhour += 12;
            fo = "AM";
        }
        else if (Nhour==12) {
            fo = "PM";
        }
        else if (Nhour>12){
            fo="PM";
        }
        else {
            fo="AM";
        }
    }
}






