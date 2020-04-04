package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refStudent;
import static com.example.edl.FBref.refTeacher;

public class infoTeacher extends AppCompatActivity {
    String phone1, uid;
    EditText  ename;
    TextView  tid, tphone, temail;
    Uteachers user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_teacher);


        temail=(TextView) findViewById(R.id.tvmail1);
        tphone=(TextView) findViewById(R.id.eTphone1);
        tid=(TextView) findViewById(R.id.tvid1);
        ename=(EditText) findViewById(R.id.eTname1);


        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();
        Query query = refTeacher.orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(VEL);
    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    user = data.getValue(Uteachers.class);
                    tphone.setText(user.getPhone());
                    tid.setText(user.getId());
                    phone1 = user.getPhone();
                    temail.setText(user.getEmail());
                    ename.setText(user.getName());

                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public void update(View view) {
        refTeacher.child(phone1).child("name").removeValue();
        refTeacher.child(phone1).child("name").setValue(ename.getText().toString());
        Toast.makeText(this, "The changes have been saved", Toast.LENGTH_LONG).show();
        }



    }
