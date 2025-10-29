package com.project.e_library.service;

import com.project.e_library.model.LibUser;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class LibUserDetailsService implements UserDetailsService {
    private final LibUserService userService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LibUser user = userService.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
