package com.projects.aeroplannerrestapi.contstants;

public class ErrorMessage {

    private ErrorMessage() {
    }

    public static final String ID = "Id";
    public static final String RESERVATION_ID = "Reservation Id";
    public static final String ROLE = "Role";
    public static final String NAME = "Name";
    public static final String USER = "User";
    public static final String EMAIL = "Email";
    public static final String FLIGHT = "Flight";
    public static final String PASSENGER = "Passenger";
    public static final String PAYMENT = "Payment";
    public static final String TICKET = "Ticket";
    public static final String RESERVATION = "Reservation";
    
    public static final String USER_NOT_FOUND = "User not found";
    public static final String FLIGHT_ID_PASSENGER_ID = "Fight Id and Passenger Id";
    public static final String USER_ALREADY_EXISTS = "User already exists with email: %s";
    public static final String TOKEN_NOT_FOUND = "Token not found";
    public static final String RESOURCE_NOT_FOUND = "%s not found with %s : %s";
    public static final String USERNAME_PASSWORD_INCORRECT = "The username or password is incorrect";
    public static final String ACCOUNT_LOCKED = "The account is locked";
    public static final String UNAUTHORIZED_USER = "You are not authorized to access this resource";
    public static final String JWT_SIGNATURE_INVALID = "The JWT signature is invalid";
    public static final String JWT_SIGNATURE_EXPIRED = "The JWT token has expired";
}
