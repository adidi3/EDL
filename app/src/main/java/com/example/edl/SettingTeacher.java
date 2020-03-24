package com.example.edl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SettingTeacher extends AppCompatActivity {

    TextView tvTimeStart, tvTimeEnd;
    Calendar ca;
    int Nhour, Nminute;
    String fo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_teacher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_teachers);
        tvTimeStart= (TextView) findViewById(R.id.tvTimeStart);
        tvTimeEnd= (TextView) findViewById(R.id.tvTimeEnd);

        ca= Calendar.getInstance();

        Nhour=ca.get(Calendar.HOUR_OF_DAY);
        Nminute=ca.get(Calendar.MINUTE);

        sTF(Nhour);
        tvTimeStart.setText("when do you start your day?");
        tvTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(SettingTeacher.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int Nhour, int Nminute) {

                        sTF(Nhour);
                        tvTimeStart.setText(Nhour+ " : "+Nminute+ " "+fo );
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

  /*  Nminute= Nminute+40;
                        if (Nminute>=60){
        Nhour=Nhour+1;
        Nminute=Nminute-60;
    }
                        if (Nhour>=24){
        Nhour=Nhour-24;
    }
**/
    public void bl(View view) {
    }

    public void br(View view) {
    }

    public void Et(View view) {
    }
}

