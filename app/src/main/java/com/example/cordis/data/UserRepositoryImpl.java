package com.example.cordis.data;

import android.util.Log;

import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class UserRepositoryImpl implements UserRepository {


    @Override
    public Boolean createUser(UserModel user) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(user.getUid()).set(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateUser(UserModel user) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(user.getUid()).set(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteUser(String uid) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(uid).delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserModel getUser(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentSnapshot doc = null;
        try {
            doc = Tasks.await(db.collection("users").document(uid).get());
        } catch (ExecutionException | InterruptedException e) {
            Log.d("tagat", "Zalooper");
        }
        if (doc != null && doc.exists()) {
            return doc.toObject(UserModel.class);
        } else {
            Log.d("tagat", "Zalooper 2");
            return null;
        }
    }
}