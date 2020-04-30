package com.example.edl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
/**
 * a class that contains references to Firebase- Authentication, Database and Storage
 */
public class FBref {
    public static FirebaseAuth refAuth= FirebaseAuth.getInstance();;

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refUsers  = FBDB.getReference("Users");
    public static DatabaseReference refTeacherTime  = FBDB.getReference("TeacherTime");
    public static DatabaseReference refStudent  = refUsers.child("Ustudents");
    public static DatabaseReference refTeacher  = refUsers.child("Uteachers");


    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference mStorageRef = FBST.getReference();
    public static StorageReference refImages = mStorageRef.child("images");

}
