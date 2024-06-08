package com.example.cordis.data;


import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public UserModel getCurrentUser() {
        DocumentSnapshot doc;
        try {
            String uid = FirebaseAuth.getInstance().getUid();
            return getUser(uid);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean logOut() {
        try {
            FirebaseAuth.getInstance().signOut();
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
        }
        if (doc != null && doc.exists()) {
            return doc.toObject(UserModel.class);
        } else {
            return null;
        }
    }
}