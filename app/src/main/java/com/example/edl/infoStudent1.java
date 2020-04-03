package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refTeacher;

public class infoStudent1 extends AppCompatActivity {
    String phone1, email1, id1, date1, name1, count1, uid;
    Spinner spinner1;
    Boolean female1,manual1;
    EditText ephone, eemail, eid, efemale, emanual, edate, ename;
    TextView ecount;
    Ustudents user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_student1);


        eemail=(EditText) findViewById(R.id.etmail1);
        ephone=(EditText) findViewById(R.id.eTphone1);
        eid=(EditText) findViewById(R.id.etid1);
        efemale=(EditText) findViewById(R.id.etfe1);
        emanual=(EditText) findViewById(R.id.etmanual1);
        edate=(EditText) findViewById(R.id.etmail1);
        ename=(EditText) findViewById(R.id.eTname1);
        ecount=(TextView) findViewById(R.id.tvcount1);

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();
        Query query = refTeacher.orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(VEL);
        eemail.setText(email1);

    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    user = data.getValue(Ustudents.class);
                    name1=user.getName();
                    phone1=user.getPhone();
                    email1=user.getEmail();
                    id1=user.getId();
                    female1=user.getFemale();
                    manual1=user.getManual();
                    count1=user.getCount();
                    date1=user.getDate();


                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

}
