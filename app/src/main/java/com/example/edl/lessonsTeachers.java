package com.example.edl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class lessonsTeachers extends AppCompatActivity {
    String phone1="0";
    String name1;
    TextView y;
   // Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        y = (TextView) findViewById(R.id.tv3);
        Intent  i = getIntent();
        phone1 = i.getExtras().getString("phone");
        name1 = i.getExtras().getString("name");

      //  y.setText(i.getStringExtra("phone"));

        // Get calendar set to current date and time
        //  Calendar c = Calendar.getInstance();

// Set the calendar to monday of the current week
        //c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

// Print dates of the current week starting on Monday
        //DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
        //for (int i = 0; i < 7; i++) {
        //  System.out.println(df.format(c.getTime()));
        // c.add(Calendar.DATE, 1);
        //}


    }


    }

