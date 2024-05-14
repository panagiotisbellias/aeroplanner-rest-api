package com.projects.aeroplannerrestapi.exception;

import com.projects.aeroplannerrestapi.dto.response.ErrorDetailsResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetailsResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception,
                                                                                 WebRequest webRequest) {
        LOG.debug(String.format("handleUserAlreadyExistsException(%s, %s)", exception, webRequest));
        return buildErrorDetailsResponse(exception.getMessage(), webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                                WebRequest webRequest) {
        LOG.debug(String.format("handleResourceNotFoundException(%s, %s)", exception, webRequest));
        return buildErrorDetailsResponse(exception.getMessage(), webRequest.getDescription(false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ErrorDetailsResponse> handleEmailSendingException(EmailSendingException exception,
                                                                            WebRequest webRequest) {
        LOG.debug(String.format("handleEmailSendingException(%s, %s)", exception, webRequest));
        return buildErrorDetailsResponse(exception.getMessage(), webRequest.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        LOG.debug(String.format("handleMethodArgumentNotValidException(%s)", exception.getMessage()));
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    LOG.info(error);
                    errors.put(fieldName, message);
                });
        LOG.info(String.format("%d error(s)", errors.size()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorDetailsResponse> handleTokenNotFoundException(TokenNotFoundException exception,
                                                                             WebRequest webRequest) {
        LOG.debug(String.format("handleTokenNotFoundException(%s, %s)", exception, webRequest));
        return buildErrorDetailsResponse(exception.getMessage(), webRequest.getDescription(false), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsResponse> handleGlobalException(Exception exception, WebRequest webRequest) {
        LOG.debug(String.format("handleGlobalException(%s, %s)", exception, webRequest));
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
        LOG.info(String.format("HTTP Status is %s%n", httpStatus));
        return buildErrorDetailsResponse(errorMessage.toString(), webRequest.getDescription(false), httpStatus);
    }

    private ResponseEntity<ErrorDetailsResponse> buildErrorDetailsResponse(String message, String path, HttpStatusCode statusCode) {
        LOG.debug(String.format("buildErrorDetailsResponse(%s, %s, %s)", message, path, statusCode));
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse();
        errorDetailsResponse.setTimestamp(LocalDateTime.now());
        errorDetailsResponse.setMessage(message);
        errorDetailsResponse.setPath(path);
        return new ResponseEntity<>(errorDetailsResponse, statusCode);
    }

}
