package com.example.cordis.ui.signup;

import static com.google.common.collect.ComparisonChain.start;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.user.CreateUserUseCase;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpViewModel extends ViewModel {
    MutableLiveData<SignUpState> signUpStatus = new MutableLiveData<>();

    public void userSignUp(String name, String email, String password) {
        signUpStatus.postValue(SignUpState.LOADING);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        UserRepository userRepository = new UserRepositoryImpl();
                        UserModel user = new UserModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), name);
                        if (CreateUserUseCase.execute(user, userRepository)) {
                            signUpStatus.postValue(SignUpState.SUCCESS);
                        } else {
                            signUpStatus.postValue(SignUpState.ERROR);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                ).start();
            } else {
                signUpStatus.postValue(SignUpState.ERROR);
            }
        });
    }
}


enum SignUpState {
    LOADING,
    SUCCESS,
    ERROR
}