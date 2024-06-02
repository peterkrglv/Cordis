package com.example.cordis.domain.user;

public class DeleteUserUseCase {
    public static Boolean execute(String uid, UserRepository userRepository) {
        return userRepository.deleteUser(uid);
    }
}
