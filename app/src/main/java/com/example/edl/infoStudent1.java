package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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
import static com.example.edl.FBref.refTeacherTime;

public class infoStudent1 extends AppCompatActivity {
    String phone1, uid;
    Boolean female1,manual1;
    EditText  efemale, emanual,  ename;
    TextView tcount, tid, tdate, tphone, temail;
    Ustudents user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_student1);


        temail=(TextView) findViewById(R.id.tvmail1);
        tphone=(TextView) findViewById(R.id.eTphone1);
        tid=(TextView) findViewById(R.id.tvid1);
        efemale=(EditText) findViewById(R.id.etfe1);
        emanual=(EditText) findViewById(R.id.etmanual1);
        ename=(EditText) findViewById(R.id.eTname1);
        tcount=(TextView) findViewById(R.id.tvcount1);
        tdate=(TextView) findViewById(R.id.tvDate1);

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();
        Query query = refStudent.orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(VEL);


    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    user = data.getValue(Ustudents.class);
                    tphone.setText(user.getPhone());
                    tid.setText(user.getId());
                    female1=user.getFemale();
                    manual1=user.getManual();
                    phone1=user.getPhone();
                    tdate.setText(user.getDate());
                    tcount.setText(user.getCount());
                    temail.setText(user.getEmail());
                    ename.setText(user.getName());

                    if(female1.equals("true")){
                        efemale.setText("female");
                    }
                    else{
                        efemale.setText("male");
                    }

                    if (manual1.equals("true"))
                        emanual.setText("manual");
                    else
                        emanual.setText("auto");


                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public void update(View view) {
       refStudent.child(phone1).child("name").removeValue();
        refStudent.child(phone1).child("name").setValue(ename.getText().toString());
        if (emanual.getText().toString().equals("manual")){
            refStudent.child(phone1).child("manual").removeValue();
            refStudent.child(phone1).child("manual").setValue(true);
        }
        else {
            if (emanual.getText().toString().equals("auto")) {
                refStudent.child(phone1).child("manual").removeValue();
                refStudent.child(phone1).child("manual").setValue(false);
            }
            else {
                Toast.makeText(this, "you wrote Invalid character!!", Toast.LENGTH_SHORT).show();
            }
        }
        if (efemale.getText().toString().equals("female")||efemale.getText().toString().equals("woman")||efemale.getText().toString().equals("Woman")||efemale.getText().toString().equals("Female")){
            refStudent.child(phone1).child("female").removeValue();
            refStudent.child(phone1).child("female").setValue(true);
        }
        else {
            if (efemale.getText().toString().equals("male")||efemale.getText().toString().equals("man")||efemale.getText().toString().equals("other")||efemale.getText().toString().equals("Man")||efemale.getText().toString().equals("Male")||efemale.getText().toString().equals("Other")) {
                refStudent.child(phone1).child("female").removeValue();
                refStudent.child(phone1).child("female").setValue(false);
            }
            else {
                Toast.makeText(this, "you wrote Invalid character!", Toast.LENGTH_SHORT).show();
            }
        }

        Toast.makeText(this, "The changes have been saved", Toast.LENGTH_LONG).show();
    }
}
