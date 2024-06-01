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
    public static final String PASSENGER_ID = "passenger id";
    public static final String FLIGHT_ID = "flight id";
    public static final String RESERVATION_ID = "reservation id";
    public static final String ISSUE_DATE = "issue date";

    public static final String INTEGRATION = "integration";
    public static final String UPDATED = "updated";

    public static final String VALID_EMAIL_ADDRESS = "sample@email.com";
    public static final String VALID_PASSWORD = "Password@123!";
    public static final String VALID_DEPARTURE_TIME = "2023-04-19T15:30:00";
    public static final String VALID_ARRIVAL_TIME = "2024-04-19T15:30:00";
    public static final String VALID_RESERVATION_DATE = "2024-05-19T15:30:00";
    public static final String VALID_CARD_NUMBER = "4532280979380570";
    public static final BigDecimal VALID_AMOUNT = BigDecimal.valueOf(100);
    public static final String VALID_EXPIRY_DATE = "11/30";
    public static final String VALID_CVV = "4431";
    public static final Long VALID_PASSENGER_ID = 1L;
    public static final Long VALID_FLIGHT_ID = 1L;
    public static final BigDecimal VALID_PRICE = BigDecimal.valueOf(0.0);

    public static final String DEFAULT_USER_ROLE = "Default user role";
    public static final String ADMINISTRATOR_ROLE = "Administrator role";

    public static final String EMAIL_PATH = "$.email";
    public static final String FULL_NAME_PATH = "$.fullName";
    public static final String TOKEN_PATH = "$.token";
    public static final String AIRLINE_PATH = "$.airline";
    public static final String FLIGHT_NUMBER_PATH = "$.flightNumber";
    public static final String DEPARTURE_TIME_PATH = "$.departureTime";
    public static final String ARRIVAL_TIME_PATH = "$.arrivalTime";
    public static final String PRICE_PATH = "$.price";
    public static final String AIRCRAFT_TYPE_PATH = "$.aircraftType";
    public static final String SEAT_AVAILABILITY_PATH = "$.seatAvailability";
    public static final String CURRENT_SEAT_AVAILABILITY_PATH = "$.currentAvailableSeat";
    public static final String AMOUNT_PATH = "$.amount";
    public static final String STATUS_PATH = "$.status";
    public static final String MESSAGE_PATH = "$.message";
    public static final String EXPIRED_IN_PATH = "$.expiredIn";
    public static final String ID_PATH = "$.id";
    public static final String PASSENGER_ID_PATH = "$.passengerId";
    public static final String FLIGHT_ID_PATH = "$.flightId";
    public static final String TRANSACTION_ID_PATH = "$.transactionId";
    public static final String CVV_PATH = "$.cvv";
    public static final String CARD_NUMBER_PATH = "$.cardNumber";
    public static final String CARD_HOLDER_NAME_PATH = "$.cardHolderName";
    public static final String EXPIRY_DATE_PATH = "$.expiryDate";
    public static final String SIZE_PATH = "$.size()";
    public static final String SEAT_NUMBER_PATH = "$.seatNumber";
    public static final String RESERVATION_DATE_PATH = "$.reservationDate";
    public static final String RESERVATION_STATUS_PATH = "$.reservationStatus";
    public static final String ISSUE_DATE_PATH = "$.issueDate";
    public static final String TICKET_STATUS_ENUM_PATH = "$.ticketStatusEnum";
}
