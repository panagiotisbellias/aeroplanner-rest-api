package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.RoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.projects.aeroplannerrestapi.util.TestConstants.DESCRIPTION;

@ExtendWith(MockitoExtension.class)
class RoleTest {

    @Mock
    User user;

    @Test
    void testAllArgsConstructor() {
        Role role = new Role(0L, RoleEnum.SUPER_ADMIN, DESCRIPTION, Set.of(user));
        assertEquals(role);
    }

    @Test
    void testNoArgsConstructor() {
        Role role = new Role();
        Assertions.assertInstanceOf(Role.class, role);
    }

    @Test
    void testSetters() {
        Role role = new Role();
        role.setId(0L);
        role.setName(RoleEnum.SUPER_ADMIN);
        role.setDescription(DESCRIPTION);
        role.setUsers(Set.of(user));
        assertEquals(role);
    }

    void assertEquals(Role role) {
        Assertions.assertEquals(0L, role.getId());
        Assertions.assertEquals(RoleEnum.SUPER_ADMIN, role.getName());
        Assertions.assertEquals(DESCRIPTION, role.getDescription());

        Set<User> users = role.getUsers();
        Assertions.assertEquals(1, users.size());
        Assertions.assertTrue(users.contains(user));
    }

}
