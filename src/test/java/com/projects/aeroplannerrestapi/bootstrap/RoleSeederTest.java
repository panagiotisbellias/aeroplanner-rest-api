package com.projects.aeroplannerrestapi.bootstrap;

import com.projects.aeroplannerrestapi.bootsrap.RoleSeeder;
import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.event.ContextRefreshedEvent;

@ExtendWith(MockitoExtension.class)
class RoleSeederTest {

    @InjectMocks
    RoleSeeder roleSeeder;

    @Mock
    RoleRepository roleRepository;

    @Test
    void testConstructor() {
        RoleSeeder roleSeeder = new RoleSeeder(roleRepository);
        Assertions.assertInstanceOf(RoleSeeder.class, roleSeeder);
    }

    @Test
    void testOnApplicationEvent() {
        ContextRefreshedEvent event = Mockito.mock(ContextRefreshedEvent.class);
        roleSeeder.onApplicationEvent(event);

        Mockito.verify(roleRepository, Mockito.times(3)).existsByName(ArgumentMatchers.any(RoleEnum.class));
        Mockito.verify(roleRepository, Mockito.times(3)).save(ArgumentMatchers.any(Role.class));
    }

}
