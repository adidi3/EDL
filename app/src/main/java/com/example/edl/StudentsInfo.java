package com.example.edl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StudentsInfo extends AppCompatActivity {
    String phone1;
    String name1;
    String money1, id1, email1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_info);

        Intent in = getIntent();
        phone1 = in.getExtras().getString("phone");
        name1 = in.getExtras().getString("name");
        money1 = in.getExtras().getString("money");
        id1= in.getExtras().getString("id");
        email1 = in.getExtras().getString("email");
    }

}
