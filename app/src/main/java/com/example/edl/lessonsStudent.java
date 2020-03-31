package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.edl.FBref.refStudent;
import static com.example.edl.FBref.refTeacher;
import static com.example.edl.FBref.refTeacherTime;

public class lessonsStudent extends AppCompatActivity implements AdapterView.OnItemClickListener{
    String phone;
    String phonestudent;
    String names;
    String count1;
    String id, email, date ;
    ListView lv1;
    ArrayList<String> stringLst= new ArrayList<String>();
    ArrayAdapter<String> adp1;
    String day="Sunday";
    TextView tvDays1, tvname;
    int dayCount=1;
    AlertDialog.Builder ad;
    LinearLayout dialog;
    Boolean female1, manual1;
    int count=0;
    Ustudents student = new Ustudents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_student);

        Intent in = getIntent();
        phone = in.getExtras().getString("phonet");
        names = in.getExtras().getString("name");
        count1=in.getExtras().getString("count");
        phonestudent=in.getExtras().getString("phones");

       lv1=(ListView)findViewById(R.id.lv11);
       tvDays1=(TextView)findViewById(R.id.tv2);
       tvname=(TextView)findViewById(R.id.tv3);

        lv1.setOnItemClickListener(this);
        lv1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp1=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,stringLst);
        lv1.setAdapter(adp1);

        final ProgressDialog progressDialog = ProgressDialog.show(this,"Login",
                "Connecting...",true);
        refStudent.child(phonestudent).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student.copyStudent(dataSnapshot.getValue(Ustudents.class));
                tvname.setText("Welcome "+student.getName());
                names=student.getName();
                count1=student.getCount();
                id=student.getId();
                email=student.getEmail();
                phone=student.getWteacher();
                date=student.getDate();
                female1=student.getFemale();
                manual1=student.getManual();


                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

            DatabaseReference refDay1 = refTeacherTime.child(phone).child(day);
        // Read from the database
        refDay1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                stringLst.clear();
                for (DataSnapshot data : ds.getChildren()){
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
                public void onDataChange(DataSnapshot ds) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    stringLst.clear();
                    for (DataSnapshot dataSnapshot1 : ds.getChildren()){
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
                public void onDataChange(DataSnapshot ds) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    stringLst.clear();
                    for (DataSnapshot dataSnapshot1 : ds.getChildren()){
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
        dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
        ad = new AlertDialog.Builder(this);
        ad.setCancelable(false);
        ad.setTitle("are you sure you want to choose this lesson?");
        ad.setView(dialog);
        ad.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //   String str = stringList.get(position);
                String str=("l"+Integer.toString(position));
                // String str= stringList.get(position);
                refTeacherTime.child(phone).child(day).child(str).removeValue();
                refTeacherTime.child(phone).child(day).child(str).setValue(phonestudent+" "+names);
                int y=Integer.parseInt(count1);
                y++;
                count1=Integer.toString(y);
                refStudent.child(phonestudent).child("count").setValue(count1);
                Toast.makeText(lessonsStudent.this, "succeeded", Toast.LENGTH_SHORT).show();
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
}
