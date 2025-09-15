package com.project.e_library.service;

import com.project.e_library.model.User;
import com.project.e_library.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserByAuthId(String authId) {
        return userRepo.findByAuthId(authId);
    }
}
