package com.example.edl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    public static FirebaseAuth refAuth= FirebaseAuth.getInstance();;

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refUsers  = FBDB.getReference("students");

    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference mStorageRef = FBST.getReference("images");
    public static StorageReference islandRef = mStorageRef.child("images/island.jpg");

}
