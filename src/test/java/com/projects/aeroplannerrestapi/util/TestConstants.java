package com.projects.aeroplannerrestapi.util;

import java.math.BigDecimal;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.*;

public class TestConstants {

    private TestConstants(){
    }

    public static final int ZERO = 0;
    public static final String ONE = "1";
    public static final String TWO = "2";

    public static final String USER_EMAIL = USER.concat(EMAIL);
    public static final String AIRLINE = "airline";
    public static final String FLIGHT_NUMBER = FLIGHT.concat("number");
    public static final String DEPARTURE_TIME = "departure time";
    public static final String ARRIVAL_TIME = "arrival time";
    public static final String AIRCRAFT_TYPE = "aircraft type";
    public static final String PASSWORD = "password";
    public static final String CARD_NUMBER = "card number";
    public static final String CARD_HOLDER_NAME = "card holder name";
    public static final String EXPIRY_DATE = "expiry date";
    public static final String CVV = "cvv";
    public static final String FULL_NAME = "full name";
    public static final String RESERVATION_DATE = "reservation date";
    public static final String MESSAGE = "message";
    public static final String PATH = "path";
    public static final String TOKEN = "token";
    public static final String TRANSACTION_ID = "transaction id";
    public static final String DESCRIPTION = "description";

    public static final String INTEGRATION = "integration";
    public static final String UPDATED = "updated";

    public static final String VALID_EMAIL_ADDRESS = "sample@email.com";
    public static final String VALID_PASSWORD = "Password@123!";
    public static final String VALID_DEPARTURE_TIME = "2023-04-19T15:30:00";
    public static final String VALID_ARRIVAL_TIME = "2024-04-19T15:30:00";
    public static final BigDecimal VALID_PRICE = BigDecimal.valueOf(0.0);


    public static final String DEFAULT_USER_ROLE = "Default user role";

    public static final String EMAIL_PATH = "$.email";
    public static final String FULL_NAME_PATH = "$.fullName";
    public static final String TOKEN_PATH = "$.token";
    public static final String AIRLINE_PATH = "$.token";
    public static final String FLIGHT_NUMBER_PATH = "$.flightNumber";
    public static final String DEPARTURE_TIME_PATH = "$.departureTime";
    public static final String ARRIVAL_TIME_PATH = "$.arrivalTime";
    public static final String PRICE_PATH = "$.price";
    public static final String AIRCRAFT_TYPE_PATH = "$.aircraftType";
    public static final String SEAT_AVAILABILITY_PATH = "$.seatAvailability";
    public static final String CURRENT_SEAT_AVAILABILITY_PATH = "$.currentAvailableSeat";
    public static final String STATUS_PATH = "$.status";
    public static final String EXPIRED_IN_PATH = "$.expiredIn";
    public static final String SIZE_PATH = "$.size()";
}
