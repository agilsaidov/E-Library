package com.project.e_library.service;

import com.project.e_library.Id.IdGenerator;
import com.project.e_library.dto.request.UserUpdateRequestDto;
import com.project.e_library.exception.DuplicateResourceException;
import com.project.e_library.exception.UserNotFoundException;
import com.project.e_library.model.LibUser;
import com.project.e_library.repo.LibUserRepo;
import com.project.e_library.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LibUserService {

    private final LibUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public LibUser registerUser(LibUser user) {
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

    public Optional<LibUser> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public LibUser loginUser(String email, String password) {

        LibUser user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Invalid Credentials"));

        if(passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new UserNotFoundException("Invalid credentials");
    }


    public LibUser updateUser(String userId, UserUpdateRequestDto userInfo) {
        LibUser user = userRepo.findByUserId(userId);
        if(user == null) {
            throw new UserNotFoundException("User not found");
        }

        user.setName(userInfo.getName());
        user.setSurname(userInfo.getSurname());
        user.setGender(userInfo.getGender());
        user.setBirthday(userInfo.getBirthdate());

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
