package com.projects.aeroplannerrestapi.repository;

import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndRoles_Name(Long id, RoleEnum roleEnum);

    Optional<User> findByIdAndRolesName(Long id, RoleEnum roleName);

    List<User> findByRoles_Name(RoleEnum roleEnum);

    void deleteByIdAndRoles_Name(Long id, RoleEnum roleEnum);
}
