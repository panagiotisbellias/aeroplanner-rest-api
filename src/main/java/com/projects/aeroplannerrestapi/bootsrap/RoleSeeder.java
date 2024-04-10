package com.projects.aeroplannerrestapi.bootsrap;

import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleEnums = new RoleEnum[]{RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN};
        Map<RoleEnum, String> roleNameDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super administrator role");
        Arrays.stream(roleEnums).forEach(roleEnum -> {
            boolean isRoleExists = roleRepository.existsByName(roleEnum);
            if (!isRoleExists) {
                Role role = new Role();
                role.setName(roleEnum);
                role.setDescription(roleNameDescriptionMap.get(roleEnum));
                roleRepository.save(role);
            }
        });
    }
}
