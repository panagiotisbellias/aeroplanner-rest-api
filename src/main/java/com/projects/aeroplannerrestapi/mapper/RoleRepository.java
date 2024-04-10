package com.projects.aeroplannerrestapi.mapper;

import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);
}
