package com.example.edl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SettingTeacher extends AppCompatActivity {

    TextView tvTimeStart, tvTimeEnd, tvDays;
    Calendar ca;
    int Nhour, Nminute, dayCount=1;
    String fo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_teacher);
        tvTimeStart = (TextView) findViewById(R.id.tvTimeStart);
        tvDays = (TextView) findViewById(R.id.textView2);
        tvTimeEnd = (TextView) findViewById(R.id.tvTimeEnd);

        ca = Calendar.getInstance();
    }

       /* Nhour=ca.get(Calendar.HOUR_OF_DAY);
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
        tvTimeEnd.setText("when do you end your day?");
        tvTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(SettingTeacher.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int Nhour, int Nminute) {

                        sTF(Nhour);
                        tvTimeEnd.setText(Nhour+ " : "+Nminute+ " "+fo );
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


    Nminute= Nminute+40;
                        if (Nminute>=60){
        Nhour=Nhour+1;
        Nminute=Nminute-60;
    }
                        if (Nhour>=24){
        Nhour=Nhour-24;
    }
**/
    public void bl(View view) {
        if (dayCount>1){
            dayCount--;
            switch (dayCount){
                case 1: tvDays.setText("Sunday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");
                break;
                case 2: tvDays.setText("Monday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
                case 3: tvDays.setText("Tuesday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
                case 4: tvDays.setText("Wednesday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
                case 5: tvDays.setText("Thursday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
            }
        }
        Toast.makeText(SettingTeacher.this, "it's the first day!", Toast.LENGTH_LONG).show();
    }

    public void br(View view) {
        if (dayCount<6){
            dayCount++;
            switch (dayCount){
                case 2: tvDays.setText("Monday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
                case 3: tvDays.setText("Tuesday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
                case 4: tvDays.setText("Wednesday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
                case 5: tvDays.setText("Thursday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
                case 6: tvDays.setText("Friday");
                    tvTimeEnd.setText("when do you end your day?");
                    tvTimeStart.setText("when do you start your day?");break;
                }
        }
        Toast.makeText(SettingTeacher.this, "it's the last day!", Toast.LENGTH_LONG).show();
    }

  /*  public void Et(View view) {
        switch (dayCount){
            case 1: break;
            case 2: break;
            case 3: break;
            case 4:break;
            case 5: break;
            case 6: ;break;
        }}
 */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu
        String st=item.getTitle().toString();
        if (st.equals("Home screen")){
            Intent gotogal = new Intent(this, lessonsTeachers.class);
            startActivity(gotogal);
        }

        return super.onOptionsItemSelected(item);
    }

    public void NW(View view) {

    }
}

