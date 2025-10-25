package com.project.e_library.repo;

import com.project.e_library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String authId);
}
