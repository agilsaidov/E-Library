package com.project.e_library.repo;

import com.project.e_library.model.LibUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibUserRepo extends JpaRepository<LibUser, Long> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String authId);

    Optional<LibUser> findByEmail(String email);

}
