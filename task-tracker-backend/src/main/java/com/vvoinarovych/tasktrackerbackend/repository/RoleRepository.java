package com.vvoinarovych.tasktrackerbackend.repository;

import com.vvoinarovych.tasktrackerbackend.model.ERole;
import com.vvoinarovych.tasktrackerbackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
