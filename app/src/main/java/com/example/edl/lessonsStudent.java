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

public class lessonsStudent extends AppCompatActivity implements AdapterView.OnItemClickListener{
    String phone="", phonestudent="", names="", count1, smoney;
    ListView lv1;
    ArrayList<String> stringLst= new ArrayList<String>();
    ArrayAdapter<String> adp1;
    String day="sunday";
    String psss="";
    String ps="";
    TextView tvDays1, tvname;
    int dayCount=1, money=0;
    AlertDialog.Builder ad, adb;
    LinearLayout dialog, dialogex;
    String uid, str, list1, tmp;
    Ustudents user;
    Uteachers usert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_student);
       lv1=(ListView)findViewById(R.id.lv11);
       tvDays1=(TextView)findViewById(R.id.tv2);
       tvname=(TextView)findViewById(R.id.tv3);

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();
        Query query = refStudent.orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(VEL);



        lv1.setOnItemClickListener(this);
        lv1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp1=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,stringLst);
        lv1.setAdapter(adp1);



    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    user = data.getValue(Ustudents.class);
                    tvname.setText("Welcome "+user.getName());
                    names=user.getName();
                    phone=user.getWteacher();
                    phonestudent=user.getPhone();
                    count1=user.getCount();
                    Query queryt = refTeacher.orderByChild("phone").equalTo(phone);
                    queryt.addListenerForSingleValueEvent(VEL2);


                }
                DatabaseReference refDay1 = refTeacherTime.child(phone).child(day);
                // Read from the database
                refDay1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds1) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        stringLst.clear();
                        for (DataSnapshot data : ds1.getChildren()){
                            String tmp=data.getValue(String.class);
                            stringLst.add(tmp);

                        }
                        adp1=new ArrayAdapter<String>(lessonsStudent.this,R.layout.support_simple_spinner_dropdown_item,stringLst);
                        lv1.setAdapter(adp1);
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

    com.google.firebase.database.ValueEventListener VEL2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot ds) {
            if (ds.exists()) {
                for (DataSnapshot data : ds.getChildren()) {
                    usert = data.getValue(Uteachers.class);
                    smoney = usert.getMoney();
                    money=Integer.parseInt(smoney);
                }}}
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };


    public void previous(View view) {
        if (dayCount>1){
            dayCount--;
            switch (dayCount){
                case 1: tvDays1.setText("Sunday");
                    day="sunday";
                    break;
                case 2: tvDays1.setText("Monday");
                    day="monday";
                    break;
                case 3: tvDays1.setText("Tuesday");
                    day="tuesday";
                    break;
                case 4: tvDays1.setText("Wednesday");
                    day="wednesday";
                    break;
            }
            DatabaseReference refDay1 = refTeacherTime.child(phone).child(day);
            // Read from the database
            refDay1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds1) {
                    stringLst.clear();
                    for (DataSnapshot dataSnapshot1 : ds1.getChildren()){
                        String tmp=dataSnapshot1.getValue(String.class);
                        stringLst.add(tmp);
                    }
                    adp1=new ArrayAdapter<String>(lessonsStudent.this,R.layout.support_simple_spinner_dropdown_item,stringLst);
                    lv1.setAdapter(adp1);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        }
        else
            Toast.makeText(lessonsStudent.this, "this is the first day!", Toast.LENGTH_LONG).show();
    }

    public void next(View view) {
        if (dayCount<5){
            dayCount++;
            switch (dayCount){
                case 2: tvDays1.setText("Monday");
                    day="monday";
                    break;
                case 3: tvDays1.setText("Tuesday");
                    day="tuesday";
                    break;
                case 4: tvDays1.setText("Wednesday");
                    day="wednesday";
                    break;
                case 5: tvDays1.setText("Thursday");
                    day="thursday";
                    break;
            }

            DatabaseReference refDay1 = refTeacherTime.child(phone).child(day);
            // Read from the database
            refDay1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds1) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    stringLst.clear();
                    for (DataSnapshot dataSnapshot1 : ds1.getChildren()){
                        String tmp=dataSnapshot1.getValue(String.class);
                        stringLst.add(tmp);

                    }
                    adp1=new ArrayAdapter<String>(lessonsStudent.this,R.layout.support_simple_spinner_dropdown_item,stringLst);
                    lv1.setAdapter(adp1);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    }
            });
        }
        else
            Toast.makeText(lessonsStudent.this, "this is the last day!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,final int position, long l) {
        list1 = stringLst.get(position);
        psss = "";

            if (!list1.equals("08:00-08:40") && !list1.equals("08:45-09:25") && !list1.equals("10:00-10:40") && !list1.equals("10:45-11:25") && !list1.equals("12:30-13:10") && !list1.equals("13:15-13:55") &&
                    !list1.equals("14:00-14:40") && !list1.equals("14:45-15:25") && !list1.equals("19:00-19:40") && !list1.equals("19:45-20:25")) {
                ps = list1;
                if ( list1.equals(phonestudent+" "+names)){
                    for (int x = 0; x <= 9; x++)
                        psss = psss + ps.charAt(x);}
                if (psss.equals(phonestudent)) {
                    dialogex = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogxxx, null);
                    adb = new AlertDialog.Builder(this);
                    adb.setCancelable(false);
                    adb.setTitle("would you like to cancel this lesson?");
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

                            refTeacherTime.child(phone).child(day).child(str).removeValue();
                            refTeacherTime.child(phone).child(day).child(str).setValue(tmp);
                            int y = Integer.parseInt(count1);
                            y--;
                            count1 = Integer.toString(y);
                            refStudent.child(phonestudent).child("count").setValue(count1);
                            Toast.makeText(lessonsStudent.this, "succeeded", Toast.LENGTH_SHORT).show();
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
                } else
                    Toast.makeText(this, "you can't choose this lesson", Toast.LENGTH_SHORT).show();
            } else {
                dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
                ad = new AlertDialog.Builder(this);
                ad.setCancelable(false);
                ad.setTitle("are you sure you want to choose this lesson?");
                ad.setView(dialog);
                ad.setPositiveButton("confirm", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        str = ("l" + (position));

                        refTeacherTime.child(phone).child(day).child(str).removeValue();
                        refTeacherTime.child(phone).child(day).child(str).setValue(phonestudent + " " + names);
                        int y = Integer.parseInt(count1);
                        y++;
                        count1 = Integer.toString(y);
                        refStudent.child(phonestudent).child("count").removeValue();
                        refStudent.child(phonestudent).child("count").setValue(count1);
                        Toast.makeText(lessonsStudent.this, "succeeded", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        if (y%money==0){
                            Toast.makeText(lessonsStudent.this, "don't forget to pay this lesson", Toast.LENGTH_LONG).show();
                        }

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
        getMenuInflater().inflate(R.menu.mainstu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected (MenuItem item){
        //menu
        String st = item.getTitle().toString();

        if (st.equals("information")) {
            Intent in = new Intent(lessonsStudent.this, infoStudent1.class);
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
