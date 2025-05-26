package com.ahmed.simpleBank.business;

import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Sets;


import org.springframework.security.core.authority.SimpleGrantedAuthority;


import static com.ahmed.simpleBank.business.UserPermission.USER_READ;
import static com.ahmed.simpleBank.business.UserPermission.USER_WRITE;

public enum UserRole {
    USER(Sets.newHashSet(USER_READ)),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
    // for permissions based authentication
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
