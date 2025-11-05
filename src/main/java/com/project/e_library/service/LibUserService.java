package com.project.e_library.service;

import com.project.e_library.Id.IdGenerator;
import com.project.e_library.dto.request.ChangePasswordDto;
import com.project.e_library.dto.request.UserUpdateRequestDto;
import com.project.e_library.exception.*;
import com.project.e_library.model.LibUser;
import com.project.e_library.repo.LibUserRepo;
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


    public Optional<LibUser> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    public LibUser loginUser(String email, String password) {

        LibUser user = userRepo.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialException("Given email or password is incorrect"));

        if(passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new InvalidCredentialException("Given email or password is incorrect");
    }


    @Transactional
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



    @Transactional
    public void changePassword(String userId, ChangePasswordDto dto) {
        LibUser user = userRepo.findByUserId(userId);
        if(user == null) {
            throw new UserNotFoundException("User not found");
        }
        if(!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Old password is incorrect");
        }

        if(passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            throw new SamePasswordException("Old and new passwords cannot be the same");
        }

        if(!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new MismatchedPasswordException("New password and confirmation password do not match");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepo.save(user);

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
