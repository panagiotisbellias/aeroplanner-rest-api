package com.projects.aeroplannerrestapi.constants;

public class PathConstants {

    private PathConstants() {
    }

    public static final String API_V1 = "/api/v1";
    public static final String ID = "/{id}";

    public static final String API_V1_AUTH = API_V1 + "/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";

    public static final String API_V1_FLIGHTS = API_V1 + "/flights";
    public static final String API_V1_PASSENGERS = API_V1 + "/passengers";
    public static final String API_V1_PAYMENTS = API_V1 + "/payments";
    public static final String PAYMENT = "/payment";

    public static final String API_V1_RESERVATIONS = API_V1 + "/reservations";
    public static final String API_V1_SUPER_ADMINS = API_V1 + "/super-admins";
    public static final String API_V1_TICKETS = API_V1 + "/tickets";
    public static final String API_V1_USERS = API_V1 + "/users";
    public static final String ME = "/me";

    public static final String ALL = "/**";
    public static final String API_V1_AUTH_ALL = "/api/v1/auth" + ALL;
    public static final String V3_API_DOCS_ALL = "/v3/api-docs" + ALL;
    public static final String SWAGGER_UI_ALL = "/swagger-ui" + ALL;
    public static final String ACTUATOR_ALL = "/actuator" + ALL;
}
