package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refStudent;
import static com.example.edl.FBref.refTeacher;
import static com.example.edl.FBref.refTeacherTime;

public class TeacherLessons extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    String phone1="", name1="", str;
    TextView  tvDays;
    int  dayCount=1;
    ListView lv;
    ArrayList<String> stringList= new ArrayList<String>();
    ArrayAdapter<String> adp;
    String day="sunday";
    Day day1= new Day();
    Week week1=new Week();
    String phonestudent="", phonestudent1;
    AlertDialog.Builder ad, adb, adb2;
    Ustudents student = new Ustudents();
    TextView vname;
    String count1;
    LinearLayout dialog, dialogex, dialogxx;
    Uteachers user;
    int t;
    String uid, tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lessons);

        lv = (ListView) findViewById(R.id.lv1);
        tvDays = (TextView) findViewById(R.id.textView2);
        vname = (TextView) findViewById(R.id.textView3);

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();
        Query query = refTeacher.orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(VEL);


        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, stringList);
        lv.setAdapter(adp);
    }



    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    user = data.getValue(Uteachers.class);
                    vname.setText("Welcome "+user.getName());
                    name1=user.getName();
                    phone1=user.getPhone();

                }
                DatabaseReference refDay = refTeacherTime.child(phone1).child(day);
                // Read from the database
                refDay.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        stringList.clear();
                        for (DataSnapshot data : ds.getChildren()){
                            String tmp=data.getValue(String.class);
                            stringList.add(tmp);
                        }
                        adp=new ArrayAdapter<String>(TeacherLessons.this,R.layout.support_simple_spinner_dropdown_item,stringList);
                        lv.setAdapter(adp);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public void bl(View view) {
        if (dayCount>1){
            dayCount--;
            switch (dayCount){
                case 1: tvDays.setText("Sunday");
                    day="sunday";
                    break;
                case 2: tvDays.setText("Monday");
                    day="monday";
                   break;
                case 3: tvDays.setText("Tuesday");
                    day="tuesday";
                    break;
                case 4: tvDays.setText("Wednesday");
                    day="wednesday";
                    break;
            }
            DatabaseReference refDay = refTeacherTime.child(phone1).child(day);
            // Read from the database
            refDay.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    stringList.clear();
                    for (DataSnapshot data : ds.getChildren()){
                        String tmp=data.getValue(String.class);
                        stringList.add(tmp);

                    }
                    adp=new ArrayAdapter<String>(TeacherLessons.this,R.layout.support_simple_spinner_dropdown_item,stringList);
                    lv.setAdapter(adp);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        }
        else
        Toast.makeText(TeacherLessons.this, "it's the first day!", Toast.LENGTH_LONG).show();
    }

    public void br(View view) {
        if (dayCount<5){
            dayCount++;
            switch (dayCount){
                case 2: tvDays.setText("Monday");
                day="monday";
                   break;
                case 3: tvDays.setText("Tuesday");
                day="tuesday";
                   break;
                case 4: tvDays.setText("Wednesday");
                day="wednesday";
                   break;
                case 5: tvDays.setText("Thursday");
                day="thursday";
                  break;
            }

            DatabaseReference refDay = refTeacherTime.child(phone1).child(day);
            // Read from the database
            refDay.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    stringList.clear();
                    for (DataSnapshot data : ds.getChildren()){
                        String tmp=data.getValue(String.class);
                        stringList.add(tmp);

                    }
                    adp=new ArrayAdapter<String>(TeacherLessons.this,R.layout.support_simple_spinner_dropdown_item,stringList);
                    lv.setAdapter(adp);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        }
        else
        Toast.makeText(TeacherLessons.this, "it's the last day!", Toast.LENGTH_LONG).show();
    }

    public void NW(View view) {
        dialogxx = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogxx, null);
        adb2 = new AlertDialog.Builder(this);
        adb2.setCancelable(false);
        adb2.setTitle("would you like to start new week?");
        adb2.setView(dialogxx);
        adb2.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                refTeacherTime.child(phone1).setValue(week1);
                refTeacherTime.child(phone1).child("sunday").setValue(day1);
                refTeacherTime.child(phone1).child("monday").setValue(day1);
                refTeacherTime.child(phone1).child("tuesday").setValue(day1);
                refTeacherTime.child(phone1).child("wednesday").setValue(day1);
                refTeacherTime.child(phone1).child("thursday").setValue(day1);
                dialogInterface.dismiss();
            }



    });
            adb2.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    });
    AlertDialog adb3 = adb2.create();
            adb3.show();

        }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        phonestudent1 = stringList.get(position);
        if (phonestudent1.equals("Canceled")) {
            dialogex = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogxxx, null);
            adb = new AlertDialog.Builder(this);
            adb.setCancelable(false);
            adb.setTitle("would you like to return this lesson?");
            adb.setView(dialogex);
            adb.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    str = ("l" + (position));

                    switch (position) {
                        case 0:
                            tmp = "08:00-08:40";
                            break;
                        case 1:
                            tmp = "08:45-09:25";
                            break;
                        case 2:
                            tmp = "10:00-10:40";
                            break;
                        case 3:
                            tmp = "10:45-11:25";
                            break;
                        case 4:
                            tmp = "12:30-13:10";
                            break;
                        case 5:
                            tmp = "13:15-13:55";
                            break;
                        case 6:
                            tmp = "14:00-14:40";
                            break;
                        case 7:
                            tmp = "14:45-15:25";
                            break;
                        case 8:
                            tmp = "19:00-19:40";
                            break;
                        case 9:
                            tmp = "19:45-20:25";
                            break;
                    }

                    refTeacherTime.child(phone1).child(day).child(str).removeValue();
                    refTeacherTime.child(phone1).child(day).child(str).setValue(tmp);
                    Toast.makeText(TeacherLessons.this, "succeeded", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }

            });
            adb.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog adb1 = adb.create();
            adb1.show();
        } else {

            dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
            ad = new AlertDialog.Builder(this);
            ad.setCancelable(false);
            ad.setTitle("Confirm deleting value from Firebase");
            ad.setView(dialog);
            ad.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String str = ("l" + position);
                    if ((!phonestudent1.equals("08:00-08:40")) && !phonestudent1.equals("08:45-09:25") && !phonestudent1.equals("10:00-10:40") && !phonestudent1.equals("10:45-11:25") && !phonestudent1.equals("12:30-13:10") && !phonestudent1.equals("13:15-13:55") &&
                            !phonestudent1.equals("14:00-14:40") && !phonestudent1.equals("14:45-15:25") && !phonestudent1.equals("19:00-19:40") && !phonestudent1.equals("19:45-20:25")) {

                        for (int x = 0; x <= 9; x++)
                            phonestudent = phonestudent + phonestudent1.charAt(x);
                        refStudent.child(phonestudent).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                student.copyStudent(dataSnapshot.getValue(Ustudents.class));
                                count1 = student.getCount();
                                t = Integer.parseInt(count1);
                                t--;
                                String sr = "count";
                                String sc = Integer.toString(t);
                                refStudent.child(phonestudent).child(sr).removeValue();
                                refStudent.child(phonestudent).child(sr).setValue(sc);
                                Toast.makeText(TeacherLessons.this, "Deleting succeeded", Toast.LENGTH_SHORT).show();
                                phonestudent = "";


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    refTeacherTime.child(phone1).child(day).child(str).removeValue();
                    refTeacherTime.child(phone1).child(day).child(str).setValue("Canceled");
                    dialogInterface.dismiss();
                }
            });
            ad.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog adb2 = ad.create();
            adb2.show();

        }
    }
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected (MenuItem item){
        //menu
        String st = item.getTitle().toString();

        if (st.equals("Students information")) {
            Intent in = new Intent(TeacherLessons.this, StudentsInfo.class);
            startActivity(in);
            finish();
        }
        if (st.equals("Information about me")) {
            Intent in = new Intent(TeacherLessons.this, infoTeacher.class);
            startActivity(in);
            finish();
        }
        if (st.equals("About")) {
            openDialog();
        }



        return super.onOptionsItemSelected(item);
    }
    public void openDialog(){
        about1 about12= new about1();
        about12.show(getSupportFragmentManager(),"About");

    }
}
