package com.example.cordis.domain.user;

public class CreateUserUseCase {
    public static Boolean execute(UserModel user, UserRepository userRepository) {
        return userRepository.createUser(user);
    }
}
