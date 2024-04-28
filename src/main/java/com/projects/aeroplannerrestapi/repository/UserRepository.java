package com.projects.aeroplannerrestapi.repository;

import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndRoles_Name(Long id, RoleEnum roleEnum);

    Optional<User> findByIdAndRolesName(Long id, RoleEnum roleName);

    void deleteByIdAndRoles_Name(Long id, RoleEnum roleEnum);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleEnum")
    Page<User> findByRole(RoleEnum roleEnum, Pageable pageable);
}
