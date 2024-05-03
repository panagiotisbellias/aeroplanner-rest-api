package com.projects.aeroplannerrestapi.constants;

public class SecurityRoleConstants {

    private SecurityRoleConstants() {
    }

    public static final int SEVEN = 7;
    public static final String BEARER = "Bearer ";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";

    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    public static final String USER_ROLE_AUTHORIZATION = "hasRole('USER')";
    public static final String ADMIN_ROLE_AUTHORIZATION = "hasRole('ADMIN')";
    public static final String SUPER_ADMIN_ROLE_AUTHORIZATION = "hasRole('SUPER_ADMIN')";
    public static final String USER_OR_ADMIN_ROLE_AUTHORIZATION = "hasAnyRole('USER', 'ADMIN')";
    public static final String SUPER_ADMIN_OR_ADMIN_ROLE_AUTHORIZATION = "hasAnyRole('SUPER_ADMIN', 'ADMIN')";
}
