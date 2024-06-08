package com.example.cordis.domain.user;

public class LogOutUseCase {
    public static void execute(UserRepository userRepository) {
        userRepository.logOut();
    }
}
