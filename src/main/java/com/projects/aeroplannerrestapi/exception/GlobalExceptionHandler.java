package com.projects.aeroplannerrestapi.exception;

import com.projects.aeroplannerrestapi.dto.ErrorDetails;
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

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyExistsException(UserAlreadyExistsException exception,
                                                                         WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalExceptionHandler(Exception exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setPath(webRequest.getDescription(false));
        StringBuilder errorMessage = new StringBuilder();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof BadCredentialsException) {
            errorMessage.append("The username or password is incorrect");
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof AccountStatusException) {
            errorMessage.append("The account is locked");
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception instanceof AccessDeniedException) {
            errorMessage.append("You are not authorized to access this resource");
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception instanceof SignatureException) {
            errorMessage.append("The JWT signature is invalid");
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception instanceof ExpiredJwtException) {
            errorMessage.append("The JWT token has expired");
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception instanceof Exception){
            errorMessage.append("Unknown internal server error.");
        }
        errorDetails.setMessage(errorMessage.toString());
        return new ResponseEntity<>(errorDetails, httpStatus);
    }
}
