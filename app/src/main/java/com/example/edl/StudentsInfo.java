package com.example.edl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refImages;
import static com.example.edl.FBref.refStudent;
import static com.example.edl.FBref.refTeacher;
/**
 * @author Adi Eisenberg
 * in this activity the teacher can see his student's details and can change part of them.
 */
public class StudentsInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner1;
    String phone1, uid, phoneteacher="", strStudents="", strphone="", mail="", uids="";
    Boolean female1,manual1;
    EditText  efemale, emanual,  ename;
    TextView tcount, tid, tdate, tphone;
    Ustudents user, users;
    ImageView iv;
    int num=0;
    Uteachers usert;
    LinearLayout dialogxxxx;
    List<String> lst = new ArrayList<String>();
    AlertDialog.Builder adb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_info);
        iv=(ImageView) findViewById(R.id.iv);
        spinner1=(Spinner)findViewById(R.id.spinner2);
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
    /**
     *the function reads from FB the students that is belong to the teacher, insert their phones and names into a spinner, and then shows the spinner.
     */
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
    /**
     *the function reads from FB the necessary details about the teacher.
     *the function calls to search in Ustudent in FB the students according to the teacher’s phone number.
     */

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
    /**
     *the function searches from the students’s branch the student that was selected.
     *the function reads  the necessary details about the student.
     *the function calls to another function in order to download the student’s profile image.
     */

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
                    mail=users.getEmail();
                    tdate.setText(users.getDate());
                    uids=users.getUid();
                    tcount.setText(users.getCount());
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

                    try {
                        download();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    /**
     *the function calls to search in students’s branch in FB the student according to the student’s phone number.
     @param view
     */

    public void show(View view) {
        Query query2 = refStudent.orderByChild("phone").equalTo(strphone);
        query2.addListenerForSingleValueEvent(VEL3);

    }
    /**
     * this function downloads the image from Firebase Storage to a local file and presents the image
     * @throws IOException
     */

    public void download() throws IOException {

        StorageReference refImg = refImages.child(uids+".jpg");

        final File localFile = File.createTempFile(uids,"jpg");
        refImg.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                String filePath = localFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                iv.setImageBitmap(bitmap);
                iv.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
    /**
     *the function happens when the button 'Update' is clicked.
     *if the teacher changed his student’s details the older details will be deleted and the new one will replace it on FB.
     * @param view
     */

    public void update(View view) {
        if (!tdate.getText().equals("")) {
            refStudent.child(phone1).child("name").removeValue();
            refStudent.child(phone1).child("name").setValue(ename.getText().toString());
            if ((emanual.getText().toString().equals("manual"))||(emanual.getText().toString().equals("Manual"))) {
                refStudent.child(phone1).child("manual").removeValue();
                refStudent.child(phone1).child("manual").setValue(true);
            } else {
                if ((emanual.getText().toString().equals("auto"))||(emanual.getText().toString().equals("Auto")))  {
                    refStudent.child(phone1).child("manual").removeValue();
                    refStudent.child(phone1).child("manual").setValue(false);
                } else {
                    Toast.makeText(this, "you wrote Invalid character!!", Toast.LENGTH_SHORT).show();
                }
            }
            if (efemale.getText().toString().equals("female") || efemale.getText().toString().equals("woman") || efemale.getText().toString().equals("Woman") || efemale.getText().toString().equals("Female")) {
                refStudent.child(phone1).child("female").removeValue();
                refStudent.child(phone1).child("female").setValue(true);
            } else {
                if (efemale.getText().toString().equals("male") || efemale.getText().toString().equals("man") || efemale.getText().toString().equals("Man") || efemale.getText().toString().equals("Male")) {
                    refStudent.child(phone1).child("female").removeValue();
                    refStudent.child(phone1).child("female").setValue(false);
                } else {
                    Toast.makeText(this, "you wrote Invalid character!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(this, "You didn't select student", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *the function takes the selected item, that includes the student's phone number and name, and isolated only the phone number.
     *In order to search in students's branch in FB  it required to search the student's phone number.
     @param adapterView
     @param view
     @param pos
     @param l

     */

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        num=pos+1;
        strphone="";
        strStudents = lst.get(pos);
        iv.setVisibility(View.INVISIBLE);
        for (int x = 0; x <= 12; x++)
            strphone = strphone + strStudents.charAt(x);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        /**
         *the function creates a menu.
         @param menu
         */

    }
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**
     *the function checked which one of the menu’s options was selected by the student, and sent him to the selected screen.
     @param item
     */

    public boolean onOptionsItemSelected (MenuItem item){
        String st = item.getTitle().toString();

        if (st.equals("Home screen")) {
            Intent in = new Intent(StudentsInfo.this, TeacherLessons.class);
            startActivity(in);
            finish();
        }
        else {    if (st.equals("Information about me")) {
            Intent in = new Intent(StudentsInfo.this, infoTeacher.class);
            startActivity(in);
            finish();
        }
        else{ if (st.equals("About")) {
            about1 about12= new about1();
            about12.show(getSupportFragmentManager(),"About");
        }}}
        return super.onOptionsItemSelected(item);
    }

    /**
     *the function happens when the button 'delete' is clicked.
     *the function delete any details about this student
     @param view
     */

    public void delete(View view) {
        if (!tdate.getText().equals("")) {
            dialogxxxx = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
            adb2 = new AlertDialog.Builder(this);
            adb2.setCancelable(false);
            adb2.setTitle("Would you like to remove this student?");
            adb2.setView(dialogxxxx);
            adb2.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    refStudent.child(phone1).child("id").removeValue();
                    refStudent.child(phone1).child("name").removeValue();
                    refStudent.child(phone1).child("uid").removeValue();
                    refStudent.child(phone1).child("female").removeValue();
                    refStudent.child(phone1).child("manual").removeValue();
                    refStudent.child(phone1).child("email").removeValue();
                    refStudent.child(phone1).child("password").removeValue();
                    refStudent.child(phone1).child("date").removeValue();
                    refStudent.child(phone1).child("wteacher").removeValue();
                    refStudent.child(phone1).child("student").removeValue();
                    refStudent.child(phone1).child("phone").removeValue();
                    refStudent.child(phone1).child("count").removeValue();
                }

            });
            adb2.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog adb1 = adb2.create();
            adb1.show();
        }
        else {
            Toast.makeText(this, "You didn't select student", Toast.LENGTH_SHORT).show();
        }
    }
}