package com.example.cordis.domain.user;

public class GetUserUseCase {
    public static UserModel execute(String uid, UserRepository userRepository) {
        return userRepository.getUser(uid);
    }
}
