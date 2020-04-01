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
    String phone1;
    String name1;
    String money1, id1, email1;
    TextView  tvDays;
 //   TextView v1;
    int  dayCount=1;
    ListView lv;
    ArrayList<String> stringList= new ArrayList<String>();
    ArrayAdapter<String> adp;
    String day="Sunday";
    Day day1= new Day();
    Week week1=new Week();
    String phonestudent="", phonestudent1;
    AlertDialog.Builder ad;
    TextView vname;
    LinearLayout dialog;
    Uteachers user = new Uteachers();
    int t;

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
        name1 = in.getExtras().getString("namet");

        lv = (ListView) findViewById(R.id.lv1);
        tvDays = (TextView) findViewById(R.id.textView2);
        vname = (TextView) findViewById(R.id.textView3);

        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,stringList);
        lv.setAdapter(adp);

        final ProgressDialog progressDialog = ProgressDialog.show(this,"Login",
                "Connecting...",true);
        refTeacher.child(phone1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.copyUser(dataSnapshot.getValue(Uteachers.class));
                vname.setText("Welcome "+user.getName());
                name1=user.getName();
                money1=user.getMoney();
                id1=user.getId();
                email1=user.getEmail();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
             }
        });

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

//        finish();
    }
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

        refTeacherTime.child(phone1).setValue(week1);
        refTeacherTime.child(phone1).child("sunday").setValue(day1);
        refTeacherTime.child(phone1).child("monday").setValue(day1);
        refTeacherTime.child(phone1).child("tuesday").setValue(day1);
        refTeacherTime.child(phone1).child("wednesday").setValue(day1);
        refTeacherTime.child(phone1).child("thursday").setValue(day1);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
        ad = new AlertDialog.Builder(this);
        ad.setCancelable(false);
        ad.setTitle("Confirm deleting value from Firebase");
        ad.setView(dialog);
        ad.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //   String str = stringList.get(position);
                String str=("l"+Integer.toString(position));
               // String str= stringList.get(position);
                phonestudent1 = stringList.get(position);
                for (int x = 0; x <= 9; x++)
                    phonestudent =phonestudent+phonestudent1.charAt(x);

          //   DatabaseReference refDay = refStudent.child(phonestudent).child("count");
                // Read from the database
                refStudent.child(phonestudent).child("count").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        for (DataSnapshot data : ds.getChildren()) {
                            String tmp = data.getValue(String.class);
                            t = Integer.parseInt(tmp);
                            t--;


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                    //  count1++;
                //refStudent.child(phonestudent).child("count").setValue(count1);
                String sr="count";
                String sc=Integer.toString(t);
                refStudent.child(phonestudent).child(sr).removeValue();
                refStudent.child(phonestudent).child(sr).setValue(sc);
                refTeacherTime.child(phone1).child(day).child(str).removeValue();
                refTeacherTime.child(phone1).child(day).child(str).setValue("Canceled");
                Toast.makeText(TeacherLessons.this, "Deleting succeeded", Toast.LENGTH_SHORT).show();
                phonestudent="";
                dialogInterface.dismiss();
            }
        });
        ad.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog adb = ad.create();
        adb.show();

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
            in.putExtra("id",id1);
            in.putExtra("name",name1);
            in.putExtra("email",email1);
            in.putExtra("phone", phone1);
            in.putExtra("money", money1);
            startActivity(in);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
