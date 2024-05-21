package com.example.cordis.data;

import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public UserModel getUser(String uid) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Task task = db.collection("users").document(uid).get();
            task.wait();
            return (UserModel) task.getResult(UserModel.class);
        } catch (Exception e) {
            return null;
        } catch (Throwable e) {
            throw new RuntimeException(e);
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
}
