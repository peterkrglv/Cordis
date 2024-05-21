package com.example.cordis.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class LogInViewModel extends ViewModel {


    MutableLiveData<LogInState> loginStatus = new MutableLiveData<>();
    public void userLogin(String email, String password) {
        loginStatus.postValue(LogInState.LOADING);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginStatus.postValue(LogInState.SUCCESS);
            } else {
                loginStatus.postValue(LogInState.ERROR);
            }
        });
    }
}


enum LogInState {
    LOADING,
    SUCCESS,
    ERROR
}