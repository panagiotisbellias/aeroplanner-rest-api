package com.projects.aeroplannerrestapi.exception;

import com.projects.aeroplannerrestapi.dto.response.ErrorDetailsResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetailsResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception,
                                                                                 WebRequest webRequest) {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse();
        errorDetailsResponse.setTimestamp(LocalDateTime.now());
        errorDetailsResponse.setMessage(exception.getMessage());
        errorDetailsResponse.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                                WebRequest webRequest) {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse();
        errorDetailsResponse.setTimestamp(LocalDateTime.now());
        errorDetailsResponse.setMessage(exception.getMessage());
        errorDetailsResponse.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(fieldName, message);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorDetailsResponse> handleTokenNotFoundException(TokenNotFoundException exception,
                                                                             WebRequest webRequest) {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse();
        errorDetailsResponse.setTimestamp(LocalDateTime.now());
        errorDetailsResponse.setMessage(exception.getMessage());
        errorDetailsResponse.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsResponse> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse();
        errorDetailsResponse.setTimestamp(LocalDateTime.now());
        errorDetailsResponse.setPath(webRequest.getDescription(false));
        StringBuilder errorMessage = new StringBuilder();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof BadCredentialsException) {
            errorMessage.append(USERNAME_PASSWORD_INCORRECT);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof AccountStatusException) {
            errorMessage.append(ACCOUNT_LOCKED);
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception instanceof AccessDeniedException) {
            errorMessage.append(UNAUTHORIZED_USER);
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception instanceof SignatureException) {
            errorMessage.append(JWT_SIGNATURE_INVALID);
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception instanceof ExpiredJwtException) {
            errorMessage.append(JWT_SIGNATURE_EXPIRED);
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception != null){
            errorMessage.append(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        errorDetailsResponse.setMessage(errorMessage.toString());
        return new ResponseEntity<>(errorDetailsResponse, httpStatus);
    }
}
