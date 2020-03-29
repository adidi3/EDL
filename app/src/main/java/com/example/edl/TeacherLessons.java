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

public class TeacherLessons extends AppCompatActivity {
    String phone1;
    String name1;
    TextView v1;

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
        refSunday.child("1").setValue("");
        refSunday.child("2").removeValue();
        refSunday.child("3").removeValue();
        refSunday.child("4").removeValue();
        refSunday.child("5").removeValue();
        refSunday.child("6").removeValue();
        refSunday.child("7").removeValue();
        refSunday.child("8").removeValue();
        refSunday.child("9").removeValue();
        refSunday.child("10").removeValue();

    }
}
