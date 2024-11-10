package com.vvoinarovych.tasktrackerbackend.security.repository;

import com.vvoinarovych.tasktrackerbackend.security.model.ERole;
import com.vvoinarovych.tasktrackerbackend.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
