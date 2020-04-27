package com.example.edl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import static com.example.edl.FBref.refAuth;
import static com.example.edl.FBref.refImages;
import static com.example.edl.FBref.refStudent;

public class infoStudent1 extends AppCompatActivity {
    String phone1, uid, uid1="";
    Boolean female1, manual1;
    EditText efemale, emanual, ename;
    TextView tcount, tid , tphone, temail;
    Ustudents user;
    ImageView iv;
    int Gallery=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_student1);


        temail = (TextView) findViewById(R.id.tvmail1);
        tphone = (TextView) findViewById(R.id.eTphone1);
        iv=(ImageView) findViewById(R.id.iv);
        tid = (TextView) findViewById(R.id.tvid1);
        efemale = (EditText) findViewById(R.id.etfe1);
        emanual = (EditText) findViewById(R.id.etmanual1);
        ename = (EditText) findViewById(R.id.eTname1);
        tcount = (TextView) findViewById(R.id.tvcount1);

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid1 = fbuser.getUid();

        try {
            download();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();
        Query query = refStudent.orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(VEL);

        super.onStart();
    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    user = data.getValue(Ustudents.class);
                    tphone.setText(user.getPhone());
                    tid.setText(user.getId());
                    female1 = user.getFemale();
                    manual1 = user.getManual();
                    phone1 = user.getPhone();
                    tcount.setText(user.getCount());
                    temail.setText(user.getEmail());
                    ename.setText(user.getName());

                    if (female1 == true) {
                        efemale.setText("female");
                    } else {
                        efemale.setText("male");
                    }

                    if (manual1 == true)
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

    public void update(View view) {
        refStudent.child(phone1).child("name").removeValue();
        refStudent.child(phone1).child("name").setValue(ename.getText().toString());
        if ((emanual.getText().toString().equals("manual"))||(emanual.getText().toString().equals("Manual")))  {
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

        Toast.makeText(this, "The changes have been saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Gallery) {
                Uri file = data.getData();
                if (file != null) {
                    final ProgressDialog pd=ProgressDialog.show(this,"Upload image","Uploading...",true);
                    StorageReference refImg = refImages.child(uid1+".jpg");
                    refImg.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    pd.dismiss();
                                    Toast.makeText(infoStudent1.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                                    try {
                                        download();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    pd.dismiss();
                                    Toast.makeText(infoStudent1.this, "Upload failed", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "No Image was selected", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    public void download() throws IOException{

        StorageReference refImg = refImages.child(uid1+".jpg");

        final File localFile = File.createTempFile(uid1,"jpg");
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
                // Toast.makeText(infoTeacher.this, "Image download failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.mainstu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected (MenuItem item){
        //menu
        String st = item.getTitle().toString();

        if (st.equals("Home screen")) {
            Intent in = new Intent(infoStudent1.this, lessonsStudent.class);
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

    public void upload(View view) {

        Intent si = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(si, Gallery);
    }
}
