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
import static com.example.edl.FBref.refTeacher;
/**
 * @author Adi Eisenberg
 * in this activity the teacher can see his details and change part of them.
 */

public class infoTeacher extends AppCompatActivity {
    String phone1, uid;
    EditText  ename;
    TextView  tid, tphone, temail;
    Uteachers user;
    ImageView iv;
    int Gallery=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_teacher);

        iv=(ImageView) findViewById(R.id.iv);
        temail=(TextView) findViewById(R.id.tvmail1);
        tphone=(TextView) findViewById(R.id.eTphone1);
        tid=(TextView) findViewById(R.id.tvid1);
        ename=(EditText) findViewById(R.id.eTname1);

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();

        Query query = refTeacher.orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(VEL);

       try {
            download();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    user = data.getValue(Uteachers.class);
                    tphone.setText(user.getPhone());
                    tid.setText(user.getId());
                    phone1 = user.getPhone();
                    temail.setText(user.getEmail());
                    ename.setText(user.getName());

                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    /**
     *the function happens when the button 'Update' is clicked.
     *if the teacher changed his details the older details will be deleted and the new one will replace it on FB
     *@param view
     */
    public void update(View view) {
        refTeacher.child(phone1).child("name").removeValue();
        refTeacher.child(phone1).child("name").setValue(ename.getText().toString());
        Toast.makeText(this, "The changes have been saved", Toast.LENGTH_LONG).show();
    }
    /**
     *the function creates a menu.
     @param menu
     */
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**
     *the function checked which one of the menu’s options was selected by the teacher, and sent him to the selected screen.
     *  @param item
     */
    public boolean onOptionsItemSelected (MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Students's information")) {
            Intent in = new Intent(infoTeacher.this, StudentsInfo.class);
            startActivity(in);
            finish();
        }
        else{ if (st.equals("Home screen")) {
            Intent in = new Intent(infoTeacher.this, TeacherLessons.class);
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
     * After the teacher chose an image from the gallery,
     * this function uploads the selected image file to the firebase storage
     * @param requestCode   The call sign of the intent that requested the result
     * @param resultCode    A code that symbols the status of the result of the activity
     * @param data          The data returned
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Gallery) {
                Uri file = data.getData();
                if (file != null) {
                    final ProgressDialog pd=ProgressDialog.show(this,"Upload image","Uploading...",true);
                    StorageReference refImg = refImages.child(uid+".jpg");
                    refImg.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    pd.dismiss();
                                    Toast.makeText(infoTeacher.this, "Image Uploaded", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(infoTeacher.this, "Upload failed", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "No Image was selected", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    /**
     * this function downloads the image from Firebase Storage to a local file and presents the image
     * @throws IOException
     */
    public void download() throws IOException{

        StorageReference refImg = refImages.child(uid+".jpg");

        final File localFile = File.createTempFile(uid,"jpg");
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
     *the function opens the gallery
     *the function occurs when the button 'Upload' is clicked.
     *the function calls to another function with parameters (reference to the gallery and the Intent) in order to upload the profile picture from gallery.
     * @param view
     */
    public void upload(View view) {
        Intent si = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(si, Gallery);
    }
}