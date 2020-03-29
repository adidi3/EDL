package com.example.edl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    public static FirebaseAuth refAuth= FirebaseAuth.getInstance();;

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refUsers  = FBDB.getReference("Users");
    public static DatabaseReference refTeacherTime  = FBDB.getReference("TeacherTime");
    public static DatabaseReference refVarible  = FBDB.getReference("Varible");
    public static DatabaseReference refStudent  = refUsers.child("Ustudents");
    public static DatabaseReference refTeacher  = refUsers.child("Uteachers");
    public static DatabaseReference refMonday  = refVarible.child("monday");
    public static DatabaseReference refSunday  = refVarible.child("sanday");
    public static DatabaseReference refThursday  = refVarible.child("thursday");
    public static DatabaseReference refWednesday  = refVarible.child("wednesday");
    public static DatabaseReference refTuesday  = refVarible.child("tuesday");

    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference mStorageRef = FBST.getReference("images");
    public static StorageReference islandRef = mStorageRef.child("images/island.jpg");

}
