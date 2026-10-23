package com.smartmess.smartmess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmess.smartmess.model.Role;
import com.smartmess.smartmess.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // Finds users by role and approval status, e.g. all STUDENTs where approved = false
    List<User> findByRoleAndApproved(Role role, boolean approved);
}