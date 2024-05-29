package com.projects.aeroplannerrestapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntityAudit implements UserDetails {

    private static final Log LOG = LogFactory.getLog(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_sequence", allocationSize = 1, initialValue = 100)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        LOG.debug("getAuthorities()");
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role r : roles) {
            String role = "ROLE_".concat(r.getName().toString());
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            list.add(authority);
            LOG.info(String.format("Retrieved role and authority: %s", role));
        }
        LOG.info(String.format("Retrieved %d authorities/roles", list.size()));
        return list;
    }

    @Override
    public String getUsername() {
        LOG.debug("getUsername()");
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        LOG.debug("isAccountNonExpired()");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        LOG.debug("isAccountNonLocked()");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        LOG.debug("isCredentialsNonExpired()");
        return true;
    }

    @Override
    public boolean isEnabled() {
        LOG.debug("isEnabled()");
        return true;
    }
}
