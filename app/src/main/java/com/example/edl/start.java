package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refUsers;

public class start extends AppCompatActivity {
    TextView tVtitle, tVregister, tvDate, tVauto, tVmanual, tVmale, tVfemale, tVteacher, tVstudent;
    EditText eTname, eTphone, eTemail, eTpass, eTid;
    CheckBox cBstayconnect;
    Button btn;
    ListView l1;
    Spinner spinner;
    Switch switchMALEfemale, switchTecherstudent, switchAutoManuel;
    String name, phone, email, password, uid, id;
    User userdb;
    DatePickerDialog.OnDateSetListener d1;
    Boolean stayConnect, registered;
    Boolean female=false;
    Boolean manual=false;
    Boolean teacher= false;



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
        l1=(ListView) findViewById(R.id.listview);
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
                        String date = day + "/" + month + "/" + year;
                        tvDate.setText(date);
                    }

                };


                regoption();


    }
    /**
     * On activity start - Checking if user already logged in.
     * If logged in & asked to be remembered - pass on.
     * <p>
     */

    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        Boolean isChecked=settings.getBoolean("stayConnect",false);
        Intent si = new Intent(start.this,Loginok.class);
        if (refAuth.getCurrentUser()!=null && isChecked) {
            stayConnect=true;
            startActivity(si);
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
                switchTecherstudent.setVisibility(View.VISIBLE);
                eTphone.setVisibility(View.VISIBLE);
                tVteacher.setVisibility(View.VISIBLE);
                tVstudent.setVisibility(View.VISIBLE);
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
                eTphone.setVisibility(View.INVISIBLE);
                tvDate.setVisibility(View.INVISIBLE);
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

            final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);
            refAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                SharedPreferences.Editor editor=settings.edit();
                                editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                editor.commit();
                                Log.d("MainActivity", "signinUserWithEmail:success");
                                Toast.makeText(start.this, "Login Success", Toast.LENGTH_LONG).show();
                                Intent si = new Intent(start.this,Loginok.class);
                                startActivity(si);
                            } else {
                                Log.d("MainActivity", "signinUserWithEmail:fail");
                                Toast.makeText(start.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        } else {
            name=eTname.getText().toString();
            id=eTid.getText().toString();
            phone=eTphone.getText().toString();
            email=eTemail.getText().toString();
            password=eTpass.getText().toString();

            final ProgressDialog pd=ProgressDialog.show(this,"Register","Registering...",true);
            refAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                SharedPreferences.Editor editor=settings.edit();
                                editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                editor.commit();
                                Log.d("MainActivity", "createUserWithEmail:success");
                                FirebaseUser user = refAuth.getCurrentUser();
                                uid = user.getUid();
                                userdb=new User(name,email,phone,uid, id, password);
                                refUsers.child(name).setValue(userdb);
                                Toast.makeText(start.this, "Successful registration", Toast.LENGTH_LONG).show();
                                Intent si = new Intent(start.this,Loginok.class);
                                startActivity(si);
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(start.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                                else {
                                    Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(start.this, "User create failed.",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }
    }

    public void switchTeacher(View view) {
        if (switchTecherstudent.isChecked()){
            tvDate.setVisibility(View.VISIBLE);
            switchMALEfemale.setVisibility(View.VISIBLE);
            l1.setVisibility(View.VISIBLE);
            tVauto.setVisibility(View.VISIBLE);
            tVmanual.setVisibility(View.VISIBLE);
            tVmale.setVisibility(View.VISIBLE);
            tVfemale.setVisibility(View.VISIBLE);
            switchAutoManuel.setVisibility(View.VISIBLE);
            teacher=true;
            if(switchMALEfemale.isChecked()){
                female=true;
            }
            else{
                female=false;
            }

            if (switchAutoManuel.isChecked()){
                manual=true;
            }
            else{
                manual=false;
            }

        }
        else {
            tvDate.setVisibility(View.INVISIBLE);
            switchAutoManuel.setVisibility(View.INVISIBLE);
            l1.setVisibility(View.INVISIBLE);
            tVauto.setVisibility(View.INVISIBLE);
            tVmanual.setVisibility(View.INVISIBLE);
            tVmale.setVisibility(View.INVISIBLE);
            tVfemale.setVisibility(View.INVISIBLE);
            switchMALEfemale.setVisibility(View.INVISIBLE);
            teacher= false;

        }
    }
}
