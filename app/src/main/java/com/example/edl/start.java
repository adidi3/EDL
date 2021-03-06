package com.example.edl;
/**
 *  * @author		Adi Eisenberg <address @adipe120003@gmail.com>
 *  * @version	    5.3(current version number of program)
 *  * @since		18/12/2019 (the date of the package the class was added)
 *  this activity is responsible for connecting and registering to the application.
 *  */


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refStudent;
import static com.example.edl.FBref.refTeacher;
import static com.example.edl.FBref.refTeacherTime;


public class start extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    TextView  tVregister, tvDate, tVauto, tVmanual, tVmale, tVfemale, tVteacher, tVstudent;
    EditText eTname, eTphone, eTemail, eTid, eTmoney;
    Button btn;
    Spinner spinner;
    Switch switchMALEfemale, switchTecherstudent, switchAutoManuel;
    String name="nnn", phoneInput="0", phone="+972", email, uid, id, money, date="", wteacher, count="0", s, sf= "", code, mVerificationId;
    Query query;
    int yearnow;
    Ustudents Ustudents1;
    Uteachers Uteachers1;
    Day day1= new Day();
    Week week1=new Week();
    DatePickerDialog.OnDateSetListener d1;
    Boolean female=false, manual=false, student= false, isUID = true, registered, mVerificationInProgress = false ;
    List<String> tl= new ArrayList<String>();
    List<String> tlname=new ArrayList<String>();
    AlertDialog.Builder adbuild, alertdialogbuilder;
    AlertDialog adCode=null, adCode22=null;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    ValueEventListener usersListener, usersListener2;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        switchMALEfemale=(Switch) findViewById(R.id.switchMALEfemale);
        switchTecherstudent=(Switch) findViewById(R.id.switchTecherstudent);
        switchAutoManuel=(Switch) findViewById(R.id.switchAutoManuel);
        tvDate=(TextView) findViewById(R.id.tvDate);
        eTname=(EditText)findViewById(R.id.eTname);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTid=(EditText)findViewById(R.id.eTid);
        eTphone=(EditText)findViewById(R.id.eTphone);
        eTmoney=(EditText)findViewById(R.id.eTmoney);
        spinner=(Spinner) findViewById(R.id.spinner);
        tVregister=(TextView) findViewById(R.id.tVregister);
        btn=(Button)findViewById(R.id.btn);
        tVmale=(TextView) findViewById(R.id.male);
        tVteacher=(TextView) findViewById(R.id.teacher);
        tVstudent=(TextView) findViewById(R.id.student);
        tVfemale=(TextView) findViewById(R.id.female);
        tVmanual=(TextView) findViewById(R.id.manual);
        tVauto=(TextView) findViewById(R.id.auto);

        registered=true;



        /**
         * this function is called when the user is in the login option but he needs to register.
         * the function "changes" the screen for the register option.
         */
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
        /**
         * This function display the date that was chosen by the student in the text view * <p>
         * checked if the student is not too young for learning driving lessons. * <p>
         */

        d1 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month2, int day2) {
                        month2 = month2 + 1;
                        date = day2 + "/" + month2 + "/" + year1;
                        yearnow= Calendar.getInstance().get(Calendar.YEAR);
                        if ((yearnow-year1>16)|| ((yearnow-year1==16)&&(month2>=6)))
                          tvDate.setText(date);
                        else
                            Toast.makeText(start.this, "You are too young to learn driving", Toast.LENGTH_SHORT).show();
                            
                    }

                };

                query=refTeacher.orderByChild("name");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    /**
                     * This function reads the phone and name of the teachers and insert all the info to spinner *
                     * checked if the student is not too young for learn driving . * <p>
                     */

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
                onVerificationStateChanged();
                regoption();
    }

     /**
      * On activity pause - If logged in & asked to be remembered - kill activity.
      */
     /**
      * this function is called when the user is in the login option but he needs to register.
      * the function changes the screen for the register option.
      */
     private void regoption() {
        SpannableString ss = new SpannableString("Don't have an account?  Register here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                eTname.setVisibility(View.VISIBLE);
                eTid.setVisibility(View.VISIBLE);
                eTmoney.setVisibility(View.VISIBLE);
                switchTecherstudent.setVisibility(View.VISIBLE);
                tVteacher.setVisibility(View.VISIBLE);
                tVstudent.setVisibility(View.VISIBLE);
                eTemail.setVisibility(View.VISIBLE);
                btn.setText("Register");
                isUID=false;
                registered=false;
               logoption();
            }
        };
        ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }
     /**
      * this function is called when the user is in the register option but he needs to log in.
      * the function "changes" the screen for the login option
      */

     private void logoption() {
        SpannableString ss = new SpannableString("Already have an account?  Login here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                eTname.setVisibility(View.INVISIBLE);
                eTid.setVisibility(View.INVISIBLE);
                eTmoney.setVisibility(View.INVISIBLE);
                tvDate.setVisibility(View.INVISIBLE);
                eTemail.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.INVISIBLE);
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
                isUID=true;
                regoption();
            }
        };
        ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

     /**
      * Logging in or Registering to the application
      * Using:   Firebase Auth with phone and sms code
      *          Firebase Realtime database with the object User to the branch Ustudents or Uteachers and teachers also to teacherTime.
      * <p>
      *    @param view
      */

     public void logorreg(View view) {
        if (registered) {
            if ((!eTphone.getText().toString().equals(""))) {
                phoneInput=eTphone.getText().toString();
                if (((phoneInput.length() != 10) || (!phoneInput.substring(0, 2).equals("05")) || Pattern.matches("[a-zA-Z]+", phoneInput) == true) ) {
                    eTphone.setError("invalid phone number");
                } else {
                    if (phone.equals("+972"))
                        for (int x = 1; x <= 9; x++)
                            phone = phone + phoneInput.charAt(x);

                    startPhoneNumberVerification(phone);
                    onVerificationStateChanged();

                     adbuild = new AlertDialog.Builder(this);
                    final EditText et = new EditText(this);
                    et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    adbuild.setMessage("enter the code you received");
                    adbuild.setTitle("Authentication");
                    adbuild.setView(et);
                    adbuild.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int whichButton) {
                            code = et.getText().toString();
                            if (!code.isEmpty())
                                verifyPhoneNumberWithCode(mVerificationId, code);
                            dialogInterface.dismiss();
                        }
                    });
                    adbuild.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int whichButton) {
                            dialogInterface.cancel();
                        }
                    });
                    adCode22 = adbuild.create();
                    adCode22.show();
                }
            }else {
                Toast.makeText(start.this, "Please, enter your phone number.", Toast.LENGTH_LONG).show();
            }

        }

        else {

            name = eTname.getText().toString();
            id = eTid.getText().toString();
            phoneInput = eTphone.getText().toString();
            email = eTemail.getText().toString();
            if (!student)
                money = eTmoney.getText().toString();
            if (student)
                s = spinner.getSelectedItem().toString();
            if (switchMALEfemale.isChecked()) {female = true; }
            if (switchAutoManuel.isChecked()) { manual = true;}
            if ((!name.isEmpty()) && (!email.isEmpty()) && (!phoneInput.isEmpty()) && (!id.isEmpty()) &&
                    (((!student) && (!money.isEmpty())) || ((student) && (!date.isEmpty())))) {

                if (((phoneInput.length() != 10) || (!phoneInput.substring(0, 2).equals("05")) || Pattern.matches("[a-zA-Z]+", phoneInput) == true) ) {
                    eTphone.setError("invalid phone number");
                }
                else {
                    if (phone.equals("+972"))
                    for (int x = 1; x <= 9; x++)
                        phone = phone + phoneInput.charAt(x);


                    startPhoneNumberVerification(phone);
                    onVerificationStateChanged();

                     alertdialogbuilder = new AlertDialog.Builder(this);
                    final EditText et = new EditText(this);
                    et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alertdialogbuilder.setMessage("enter the code you received");
                    alertdialogbuilder.setTitle("Authentication");
                    alertdialogbuilder.setView(et);
                    alertdialogbuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int whichButton) {
                            code = et.getText().toString();
                            if (!code.isEmpty())
                                verifyPhoneNumberWithCode(mVerificationId, code);
                            dialogInterface.dismiss();
                        }
                    });
                    alertdialogbuilder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int whichButton) {
                            dialogInterface.cancel();
                        }
                    });
                    adCode = alertdialogbuilder.create();
                    adCode.show();
                }
            }

             else {
                Toast.makeText(start.this, "Please, fill all the necessary details.", Toast.LENGTH_LONG).show();
            }
        }
    }

     /**
      * the function dismiss the dialog before the new activity will start.
      */
     @Override
     protected void onStop() {
         super.onStop();
         if(adCode22!=null)
         {
             adCode22.dismiss();
         }
        else {
             if(adCode!=null)
             {
                 adCode.dismiss();
             }
        }


     }
    /**
     * this function is called when the user wants to register.
     * the function sends sms to his phone with a verification code.
     * @param	phoneNumber the user's phone number. The SMS is sent to this phone number.
     */

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                40,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
        mVerificationInProgress = true;
    }

    /**
     * this function is called in order to check if the code the user wrote is the code he received and create a credential.
     * if he wrote a right code, "signInWithPhoneAuthCredential" function is called.
     * @param	code the code that the
     * @param verificationId a verification identity to connect with firebase servers.
     */
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    /**
     * this function is called to sign in the user.
     * if the credential is proper the user is signs in and he sent to the next activity
     * @param	credential is a credential that everything was right and the user can sign in.
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        refAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");


                            FirebaseUser user = refAuth.getCurrentUser();
                            uid = user.getUid();
                            if (!isUID) {
                                if(student){
                                        for (int i = 0; i <= 12; i++)
                                            sf =sf+s.charAt(i);
                                        wteacher = sf;
                                        Ustudents1 = new Ustudents(name, email, phone, uid, id, student, manual, female, date, wteacher, count);
                                        refStudent.child(phone).setValue(Ustudents1);
                                    }
                                    else {
                                        Uteachers1 = new Uteachers(name, email, phone, uid, id, student, money);
                                        refTeacher.child(phone).setValue(Uteachers1);
                                        refTeacherTime.child(phone).setValue(week1);
                                        refTeacherTime.child(phone).child("sunday").setValue(day1);
                                        refTeacherTime.child(phone).child("monday").setValue(day1);
                                        refTeacherTime.child(phone).child("tuesday").setValue(day1);
                                        refTeacherTime.child(phone).child("wednesday").setValue(day1);
                                        refTeacherTime.child(phone).child("thursday").setValue(day1);
                                    }
                            }
                            setUsersListener();
                        }

                        else {
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(start.this, "wrong!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * this function checks the status of the verification, if it's completed, failed or inProgress.
     */
    private void onVerificationStateChanged() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    eTphone.setError("Invalid phone number");
                } else { if (e instanceof FirebaseTooManyRequestsException) {
                    }
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
            }
        };
    }

    /**
     * this function connects the current user with his information in the database by checking his uid,
     * in order to check his status and sent him to the next activity.
     */

    public void setUsersListener() {
       user = refAuth.getCurrentUser();


        usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Ustudents1=data.getValue(Ustudents.class);
                    if (Ustudents1.getStudent()) {
                        if (user.getUid().equals(data.getValue(Ustudents.class).getUid())){
                            Intent si = new Intent(start.this, lessonsStudent.class);
                            startActivity(si);
                            finish();
                        }
                    }
                    else{
                        Uteachers1=data.getValue(Uteachers.class);
                        if (!Uteachers1.getStudent()) {
                            if (user.getUid().equals(data.getValue(Uteachers.class).getUid())) {
                              Intent si = new Intent(start.this, TeacherLessons.class);
                              startActivity(si);
                             finish();
    }
}
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        usersListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Uteachers1=data.getValue(Uteachers.class);
                        if (Uteachers1.getStudent()) {
                            if (user.getUid().equals(data.getValue(Uteachers.class).getUid())) {
                                onDestroy();
                                Intent si = new Intent(start.this, TeacherLessons.class);
                                startActivity(si);
                                finish();
                            }
                        }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
          refStudent.addValueEventListener(usersListener);
          refTeacher.addValueEventListener(usersListener);

    }


     /**
      *this function gets the information about the user's type (student/ teacher) in order to give them the right supplements activity
      * @param view
      */
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