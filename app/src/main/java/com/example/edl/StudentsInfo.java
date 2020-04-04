package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refStudent;
import static com.example.edl.FBref.refTeacher;

public class StudentsInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner1;
    String phone1, uid, phoneteacher="", strStudents="", strphone="";
    Boolean female1,manual1;
    EditText  efemale, emanual,  ename;
    TextView tcount, tid, tdate, tphone, temail;
    Ustudents user, users;
    int num=0;
    Uteachers usert;
    List<String> lst = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_info);
        spinner1=(Spinner)findViewById(R.id.spinner2);
        temail=(TextView) findViewById(R.id.tvmail1);
        tphone=(TextView) findViewById(R.id.eTphone1);
        tid=(TextView) findViewById(R.id.tvid1);
        efemale=(EditText) findViewById(R.id.etfe1);
        emanual=(EditText) findViewById(R.id.etmanual1);
        ename=(EditText) findViewById(R.id.eTname1);
        tcount=(TextView) findViewById(R.id.tvcount1);
        tdate=(TextView) findViewById(R.id.tvDate1);

        lst.clear();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentsInfo.this, android.R.layout.simple_spinner_item, lst);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(this);

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();
        Query query1 = refTeacher.orderByChild("uid").equalTo(uid);
        query1.addListenerForSingleValueEvent(VEL2);





    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                lst.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    user = data.getValue(Ustudents.class);
                    String names=user.getName();
                    String phones=user.getPhone();
                    String studentInfo = phones+" "+names;
                    lst.add(studentInfo);
                }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentsInfo.this, android.R.layout.simple_spinner_item, lst);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(arrayAdapter);


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    com.google.firebase.database.ValueEventListener VEL2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    usert = data.getValue(Uteachers.class);
                    phoneteacher=usert.getPhone();
                    Query query = refStudent.orderByChild("wteacher").equalTo(phoneteacher);
                    query.addListenerForSingleValueEvent(VEL);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    com.google.firebase.database.ValueEventListener VEL3 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    users = data.getValue(Ustudents.class);
                    tphone.setText(users.getPhone());
                    tid.setText(users.getId());
                    female1=users.getFemale();
                    manual1=users.getManual();
                    phone1=users.getPhone();
                    tdate.setText(users.getDate());
                    tcount.setText(users.getCount());
                    temail.setText(users.getEmail());
                    ename.setText(users.getName());

                    if(female1==true){
                        efemale.setText("female");
                    }
                    else{
                        efemale.setText("male");
                    }

                    if (manual1==true)
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

    public void show(View view) {
        Query query2 = refStudent.orderByChild("phone").equalTo(strphone);
        query2.addListenerForSingleValueEvent(VEL3);
    }

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

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        num=pos+1;
        strphone="";
        strStudents = lst.get(pos);
        for (int x = 0; x <= 9; x++)
            strphone = strphone + strStudents.charAt(x);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected (MenuItem item){
        //menu
        String st = item.getTitle().toString();

        if (st.equals("Home screen")) {
            Intent in = new Intent(StudentsInfo.this, TeacherLessons.class);
            startActivity(in);
            finish();
        }
        if (st.equals("Information about me")) {
            Intent in = new Intent(StudentsInfo.this, infoTeacher.class);
            startActivity(in);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
