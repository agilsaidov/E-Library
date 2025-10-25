package com.project.e_library.service;

import com.project.e_library.Id.IdGenerator;
import com.project.e_library.exception.DuplicateResourceException;
import com.project.e_library.model.User;
import com.project.e_library.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(User user) {
        if(user == null || user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }

        if(userRepo.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("The given email is already in use");
        }

        String userId = generateUniqueUserId(IdGenerator.generateUserId());
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }







    //Helper Method
    private String generateUniqueUserId(String userId){
        final int maxRetries = 5;
        String id = userId;

        for(int i = 0; i < maxRetries; i++) {
            if(!userRepo.existsByUserId(id)) {
                return id;
            }

            id = IdGenerator.generateUserId();
        }
        throw new RuntimeException("ID generation failed");
    }

}
