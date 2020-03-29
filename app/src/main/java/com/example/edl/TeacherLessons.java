package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refSunday;
import static com.example.edl.FBref.refTeacher;
import static com.example.edl.FBref.refTeacherTime;

public class TeacherLessons extends AppCompatActivity {
    String phone1;
    String name1;
    TextView v1;
    Day day1= new Day();
    Week week1=new Week();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lessons);
     //   FirebaseUser firebaseUser= refAuth.getCurrentUser();
      //  phone1=firebaseUser.getPhoneNumber();
       // v1=(TextView) findViewById(R.id.textView3);
        //v1.setText(phone1);
        Intent in = getIntent();
        phone1 = in.getExtras().getString("phone");
        name1 = in.getExtras().getString("nameS");
//        finish();
    }

    public void next(View view) {

        refTeacherTime.child(phone1).setValue(week1);
        refTeacherTime.child(phone1).child("sunday").setValue(day1);
        refTeacherTime.child(phone1).child("monday").setValue(day1);
        refTeacherTime.child(phone1).child("tuesday").setValue(day1);
        refTeacherTime.child(phone1).child("wednesday").setValue(day1);
        refTeacherTime.child(phone1).child("thursday").setValue(day1);

    }
}
