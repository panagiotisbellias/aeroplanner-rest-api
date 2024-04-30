package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.RoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @InjectMocks
    User user;

    @Mock
    Role role;

    @Test
    void testAllArgsConstructor() {
        User user = new User(0L, "full name", "email", "password", Set.of(role));
        assertEquals(user);
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        Assertions.assertInstanceOf(User.class, user);
    }

    @Test
    void testSetters() {
        User user = new User();
        user.setId(0L);
        user.setFullName("full name");
        user.setEmail("email");
        user.setPassword("password");
        user.setRoles(Set.of(role));
        assertEquals(user);
    }

    void assertEquals(User user) {
        Assertions.assertEquals(0L, user.getId());
        Assertions.assertEquals("full name", user.getFullName());
        Assertions.assertEquals("email", user.getEmail());
        Assertions.assertEquals("password", user.getPassword());
        Assertions.assertEquals(Set.of(role), user.getRoles());
    }

    @Test
    void testGetAuthorities() {
        User user = new User();
        Role role = Mockito.mock(Role.class);

        Mockito.when(role.getName()).thenReturn(RoleEnum.ADMIN);
        user.setRoles(Set.of(role));

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Assertions.assertEquals(1, authorities.size());

        Optional<? extends GrantedAuthority> authority = authorities.stream().findFirst();
        Assertions.assertTrue(authorities.stream().findFirst().isPresent());
        Assertions.assertEquals("ROLE_ADMIN", authority.get().getAuthority());
    }

    @Test
    void testGetUsername() {
        Assertions.assertNull(user.getUsername());
    }

    @Test
    void testBooleanGetters() {
        Assertions.assertTrue(user.isAccountNonExpired());
        Assertions.assertTrue(user.isAccountNonLocked());
        Assertions.assertTrue(user.isCredentialsNonExpired());
        Assertions.assertTrue(user.isEnabled());
    }

}
