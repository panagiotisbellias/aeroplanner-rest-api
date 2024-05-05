package com.projects.aeroplannerrestapi.bootsrap;

import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.NAME;
import static com.projects.aeroplannerrestapi.constants.ErrorMessage.ROLE;

@Component
@RequiredArgsConstructor
@Order(2)
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${super.admin.name}")
    private String superAdminName;
    @Value("${super.admin.email}")
    private String superAdminEmail;
    @Value("${super.admin.password}")
    private String superAdminPassword;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        if (!userRepository.existsByEmail(superAdminEmail)) {
            Optional<Role> role = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
            if (role.isEmpty()) throw new ResourceNotFoundException(ROLE, NAME, RoleEnum.SUPER_ADMIN.name());
            User user = new User();
            user.setFullName(superAdminName);
            user.setEmail(superAdminEmail);
            user.setPassword(passwordEncoder.encode(superAdminPassword));
            Set<Role> set = new HashSet<>();
            set.add(role.get());
            user.setRoles(set);
            userRepository.save(user);
        }
    }
}
