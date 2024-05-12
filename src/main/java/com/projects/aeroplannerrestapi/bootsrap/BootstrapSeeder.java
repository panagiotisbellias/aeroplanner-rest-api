package com.projects.aeroplannerrestapi.bootsrap;

import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.NAME;
import static com.projects.aeroplannerrestapi.constants.ErrorMessage.ROLE;

@Component
@RequiredArgsConstructor
public class BootstrapSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private static final Log LOG = LogFactory.getLog(BootstrapSeeder.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${super.admin.name}")
    private String superAdminName;

    @Value("${super.admin.email}")
    private String superAdminEmail;

    @Value("${super.admin.password}")
    private String superAdminPassword;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.debug(String.format("onApplicationEvent(%s)", event.toString()));
        this.loadRoles();
        this.createSuperAdministrator();
    }

    private void loadRoles() {
        LOG.debug("loadRoles()");
        RoleEnum[] roleEnums = new RoleEnum[]{RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN};
        Map<RoleEnum, String> roleNameDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super administrator role");
        Arrays.stream(roleEnums).forEach(roleEnum -> {
            if (!roleRepository.existsByName(roleEnum)) {
                LOG.info(String.format("Role %s does not exist", roleEnum));
                Role role = new Role();
                role.setName(roleEnum);
                role.setDescription(roleNameDescriptionMap.get(roleEnum));
                roleRepository.save(role);
                LOG.info(String.format("Role %s with name '%s' created", role.getId(), roleEnum));
            }
        });
    }

    private void createSuperAdministrator() {
        LOG.debug("createSuperAdministrator()");
        if (!userRepository.existsByEmail(superAdminEmail)) {
            LOG.info(String.format("Super administrator with email '%s' does not exist", superAdminEmail));
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
            LOG.info(String.format("Super administrator '%s' created", user.getFullName()));
        }
    }

}
