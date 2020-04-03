package com.example.edl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refTeacher;

public class StudentsInfo extends AppCompatActivity {
    String phone1, email1, id1, female1, manual1, date1, name1, count1, uid;
    Spinner spinner1;
    EditText ephone, eemail, eid, efemale, emanual, edate, ename;
    TextView ecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_info);

        eemail=(EditText) findViewById(R.id.etmail1);
        ephone=(EditText) findViewById(R.id.eTphone1);
        eid=(EditText) findViewById(R.id.etid1);
        efemale=(EditText) findViewById(R.id.etfe1);
        emanual=(EditText) findViewById(R.id.etmanual1);
        edate=(EditText) findViewById(R.id.etmail1);
        ename=(EditText) findViewById(R.id.eTname1);
        ecount=(TextView) findViewById(R.id.tvcount1);




    }

    public void show(View view) {
    }
}
