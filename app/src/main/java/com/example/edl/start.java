package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.edl.FBref.refAllUsers;
import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refStudent;
import static com.example.edl.FBref.refTeacher;
import static com.example.edl.FBref.refTeacherTime;
import static com.example.edl.FBref.refUsers;
import static com.example.edl.FBref.refVarible;


public class start extends AppCompatActivity {
    TextView tVtitle, tVregister, tvDate, tVauto, tVmanual, tVmale, tVfemale, tVteacher, tVstudent;
    EditText eTname, eTphone, eTemail, eTpass, eTid, eTmoney;
    CheckBox cBstayconnect;
    Button btn;
    Spinner spinner;
    Switch switchMALEfemale, switchTecherstudent, switchAutoManuel;
    String name="nnn", phone="0", email, password, uid, id, money, date, wteacher;
    Query query;
    SpinnerAdapter A1;
    String count="0";
    Ustudents Ustudents1;
    Uteachers Uteachers1;
    String s;
    String sf= "";
    Day day1= new Day();
    Week week1=new Week();
    DatePickerDialog.OnDateSetListener d1;
    Boolean stayConnect, registered;
    Boolean female=false;
    Boolean manual=false;
    Boolean student= false;
    DatabaseReference reff;
    ArrayAdapter<String> adp;
    Character r;
    String e;
    List<String> tl= new ArrayList<String>();
    List<String> tlname=new ArrayList<String>();
    String uiduser;
    Uteachers usert;
    Ustudents users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        switchMALEfemale=(Switch) findViewById(R.id.switchMALEfemale);
        switchTecherstudent=(Switch) findViewById(R.id.switchTecherstudent);
        switchAutoManuel=(Switch) findViewById(R.id.switchAutoManuel);
        tVtitle=(TextView) findViewById(R.id.tVtitle);
        tvDate=(TextView) findViewById(R.id.tvDate);
        eTname=(EditText)findViewById(R.id.eTname);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTpass=(EditText)findViewById(R.id.eTpass);
        eTid=(EditText)findViewById(R.id.eTid);
        eTphone=(EditText)findViewById(R.id.eTphone);
        eTmoney=(EditText)findViewById(R.id.eTmoney);
        cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);
        spinner=(Spinner) findViewById(R.id.spinner);
        tVregister=(TextView) findViewById(R.id.tVregister);
        btn=(Button)findViewById(R.id.btn);
        tVmale=(TextView) findViewById(R.id.male);
        tVteacher=(TextView) findViewById(R.id.teacher);
        tVstudent=(TextView) findViewById(R.id.student);
        tVfemale=(TextView) findViewById(R.id.female);
        tVmanual=(TextView) findViewById(R.id.manual);
        tVauto=(TextView) findViewById(R.id.auto);

        stayConnect=false;
        registered=true;




        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calenda1 = Calendar.getInstance();
                int year = calenda1.get(Calendar.YEAR);
                int month = calenda1.get(Calendar.MONTH);
                int day = calenda1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(start.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, d1, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
            });
                d1 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int day, int month, int year) {
                        month = month + 1;
                        date = day + "/" + month + "/" + year;
                        tvDate.setText(date);
                    }

                };



        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
              //  R.array.teacher_array, android.R.layout.simple_spinner_item);
              //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                query=refTeacher.orderByChild("name");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tl.clear();
                        tlname.clear();
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            String teacherN= (String) ds.child("name").getValue();
                            String teacherP= (String) ds.child("phone").getValue();
                            tlname.add(teacherP);
                            tl.add(teacherP+ " "+ teacherN );
                        }
                        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(start.this, android.R.layout.simple_spinner_dropdown_item, tl);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                regoption();
    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    usert = data.getValue(Uteachers.class);
                    student=usert.getStudent();
                    phone=usert.getPhone();
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    com.google.firebase.database.ValueEventListener VEL2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    users = data.getValue(Ustudents.class);
                    student=users.getStudent();
                    phone=users.getPhone();
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    /**
     * On activity start - Checking if user already logged in.
     * If logged in & asked to be remembered - pass on.
     * <p>
     */
    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        Boolean isChecked=settings.getBoolean("stayConnect",false);
        FirebaseUser fbuser = refAuth.getCurrentUser();
        uiduser = fbuser.getUid();
        Query query = refTeacher.orderByChild("uid").equalTo(uiduser);
        query.addListenerForSingleValueEvent(VEL);
        Query query2 = refStudent.orderByChild("uid").equalTo(uiduser);
        query2.addListenerForSingleValueEvent(VEL2);
        if (student) {
            Intent si = new Intent(start.this, TeacherLessons.class);
            if (refAuth.getCurrentUser() != null && isChecked) {
                stayConnect = true;
                startActivity(si);
                finish();
            }
        }
        else {
            Intent si = new Intent(start.this, TeacherLessons.class);
            if (refAuth.getCurrentUser() != null && isChecked) {
                stayConnect = true;
                startActivity(si);
                finish();
            }

        }
    }
    /**
     * On activity pause - If logged in & asked to be remembered - kill activity.
     * <p>
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
    }

    private void regoption() {
        SpannableString ss = new SpannableString("Don't have an account?  Register here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Register");
                eTname.setVisibility(View.VISIBLE);
                eTid.setVisibility(View.VISIBLE);
                eTmoney.setVisibility(View.VISIBLE);
                switchTecherstudent.setVisibility(View.VISIBLE);
                tVteacher.setVisibility(View.VISIBLE);
                tVstudent.setVisibility(View.VISIBLE);
                eTphone.setVisibility(View.VISIBLE);
                btn.setText("Register");
                registered=false;
               logoption();
            }
        };
        ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void logoption() {
        SpannableString ss = new SpannableString("Already have an account?  Login here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Login");
                eTname.setVisibility(View.INVISIBLE);
                eTid.setVisibility(View.INVISIBLE);
                eTmoney.setVisibility(View.INVISIBLE);
                tvDate.setVisibility(View.INVISIBLE);
                eTphone.setVisibility(View.INVISIBLE);
                switchTecherstudent.setVisibility(View.INVISIBLE);
                switchAutoManuel.setVisibility(View.INVISIBLE);
                switchMALEfemale.setVisibility(View.INVISIBLE);
                tVfemale.setVisibility(View.INVISIBLE);
                tVmale.setVisibility(View.INVISIBLE);
                tVauto.setVisibility(View.INVISIBLE);
                tVmanual.setVisibility(View.INVISIBLE);
                tVstudent.setVisibility(View.INVISIBLE);
                tVteacher.setVisibility(View.INVISIBLE);
                btn.setText("Login");
                registered=true;
                regoption();
            }
        };
        ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Logging in or Registering to the application
     * Using:   Firebase Auth with email & password
     *          Firebase Realtime database with the object User to the branch Users
     * If login or register process is Ok saving stay connect status & pass to next activity
     * <p>
     */
    public void logorreg(View view) {
        if (registered) {
            email=eTemail.getText().toString();
            password=eTpass.getText().toString();
            ValueEventListener mrListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {
                    for (DataSnapshot data : ds.getChildren()) {
                        eTemail.setText(refAuth.getCurrentUser().getEmail());
                        //student = refAuth.getCurrentUser().getStudent();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            if (mrListener!=null) {
                refUsers.removeEventListener(mrListener);
            }

            final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);
            refAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                FirebaseUser fbuser = refAuth.getCurrentUser();
                                uiduser = fbuser.getUid();
                                Query query = refTeacher.orderByChild("uid").equalTo(uiduser);
                                query.addListenerForSingleValueEvent(VEL);
                                Query query2 = refStudent.orderByChild("uid").equalTo(uiduser);
                                query2.addListenerForSingleValueEvent(VEL2);
                               // phone = eTphone.getText().toString();

                                SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                SharedPreferences.Editor editor=settings.edit();
                                editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                editor.commit();
                                Log.d("start", "signinUserWithEmail:success");
                                Toast.makeText(start.this, "Login Success", Toast.LENGTH_LONG).show();
                               if (student) {
                                            Intent in = new Intent(start.this,lessonsStudent.class);
                                            startActivity(in);
                                            finish();
                                        }
                                        else {
                                            Intent in = new Intent(start.this, TeacherLessons.class);
                                            startActivity(in);
                                            finish();
                                        }

                            } else {
                                Log.d("start", "signinUserWithEmail:fail");
                                Toast.makeText(start.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else {
            name = eTname.getText().toString();
            id = eTid.getText().toString();
            phone = eTphone.getText().toString();
            email = eTemail.getText().toString();
            password = eTpass.getText().toString();
            money = eTmoney.getText().toString();
            s = spinner.getSelectedItem().toString();

            if (switchMALEfemale.isChecked()) {female = true; }
            if (switchAutoManuel.isChecked()) { manual = true;}
            if ((!name.isEmpty()) && (!email.isEmpty()) && (!password.isEmpty()) && (!phone.isEmpty()) && (!id.isEmpty()) && (((!student) && (!money.isEmpty())) || ((student) && (!date.isEmpty())))) {
                final ProgressDialog pd = ProgressDialog.show(this, "Register", "Registering...", true);
                refAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pd.dismiss();
                                if (task.isSuccessful()) {
                                    SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                    editor.commit();
                                    Log.d("start", "createUserWithEmail:success");
                                    FirebaseUser user = refAuth.getCurrentUser();
                                    uid = user.getUid();
                                    if (student) {
                                        for (int i = 0; i <= 9; i++)
                                            sf =sf+s.charAt(i);
                                        wteacher = sf;
                                        Ustudents1 = new Ustudents(name, email, phone, uid, id, password, student, manual, female, date, wteacher, count);
                                        refStudent.child(phone).setValue(Ustudents1);
                                        refAllUsers.child(phone).setValue(Ustudents1);
                                        Toast.makeText(start.this, "Successful registration", Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(start.this, lessonsStudent.class);
                                        startActivity(in);
                                        finish();
                                    }
                                    else {
                                        Uteachers1 = new Uteachers(name, email, phone, uid, id, password, student, money);
                                        refTeacher.child(phone).setValue(Uteachers1);
                                        refAllUsers.child(phone).setValue(Uteachers1);
                                        refTeacherTime.child(phone).setValue(week1);
                                        refTeacherTime.child(phone).child("sunday").setValue(day1);
                                        refTeacherTime.child(phone).child("monday").setValue(day1);
                                        refTeacherTime.child(phone).child("tuesday").setValue(day1);
                                        refTeacherTime.child(phone).child("wednesday").setValue(day1);
                                        refTeacherTime.child(phone).child("thursday").setValue(day1);
                                        Toast.makeText(start.this, "Successful registration", Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(start.this, TeacherLessons.class);
                                        startActivity(in);
                                        finish();
                                    }
                                }
                                else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                        Toast.makeText(start.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                                    else {
                                        Log.w("start", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(start.this, "User create failed.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            } else {
                Toast.makeText(start.this, "Please, fill all the necessary details.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void switchTeacher(View view) {
        if (switchTecherstudent.isChecked()){
            tvDate.setVisibility(View.VISIBLE);
            switchMALEfemale.setVisibility(View.VISIBLE);
            tVauto.setVisibility(View.VISIBLE);
            tVmanual.setVisibility(View.VISIBLE);
            tVmale.setVisibility(View.VISIBLE);
            tVfemale.setVisibility(View.VISIBLE);
            eTmoney.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.VISIBLE);
            switchAutoManuel.setVisibility(View.VISIBLE);
            student=true;
        }
        else {
            tvDate.setVisibility(View.INVISIBLE);
            eTmoney.setVisibility(View.VISIBLE);
            switchAutoManuel.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.INVISIBLE);
            tVauto.setVisibility(View.INVISIBLE);
            tVmanual.setVisibility(View.INVISIBLE);
            tVmale.setVisibility(View.INVISIBLE);
            tVfemale.setVisibility(View.INVISIBLE);
            switchMALEfemale.setVisibility(View.INVISIBLE);
            student = false;

        }
    }



}
