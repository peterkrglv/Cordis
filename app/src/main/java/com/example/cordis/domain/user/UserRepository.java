package com.example.cordis.domain.user;

import com.google.firebase.firestore.auth.User;

public interface UserRepository {
    Boolean createUser(UserModel user);
    Boolean updateUser(UserModel user);
    UserModel getUser(String uid);
    Boolean deleteUser(String uid);
    UserModel getCurrentUser();
    Boolean logOut();
}
