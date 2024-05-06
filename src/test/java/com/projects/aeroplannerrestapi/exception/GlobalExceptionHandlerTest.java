package com.projects.aeroplannerrestapi.exception;

import com.projects.aeroplannerrestapi.dto.response.ErrorDetailsResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Mock
    WebRequest webRequest;

    @Test
    void testHandleUserAlreadyExistsException() {
        UserAlreadyExistsException userAlreadyExistsException = Mockito.mock(UserAlreadyExistsException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleUserAlreadyExistsException(userAlreadyExistsException, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertNull(Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException resourceNotFoundException = Mockito.mock(ResourceNotFoundException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleResourceNotFoundException(resourceNotFoundException, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertNull(Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testHandleEmailSendingException() {
        EmailSendingException emailSendingException = Mockito.mock(EmailSendingException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleEmailSendingException(emailSendingException, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertNull(Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        MethodArgumentNotValidException methodArgumentNotValidException = Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        FieldError error = Mockito.mock(FieldError.class);

        Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(error));
        Mockito.when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleMethodArgumentNotValidException(methodArgumentNotValidException);
        Assertions.assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testHandleTokenNotFoundException() {
        TokenNotFoundException exception = Mockito.mock(TokenNotFoundException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleTokenNotFoundException(exception, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertNull(Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testHandleGlobalException() {
        Exception exception = Mockito.mock(Exception.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleGlobalException(exception, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testHandleGlobalExceptionExpiredJwtException() {
        ExpiredJwtException exception = Mockito.mock(ExpiredJwtException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleGlobalException(exception, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertEquals(JWT_SIGNATURE_EXPIRED, Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testHandleGlobalExceptionSignatureException() {
        SignatureException exception = Mockito.mock(SignatureException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleGlobalException(exception, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertEquals(JWT_SIGNATURE_INVALID, Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testHandleGlobalExceptionAccessDeniedException() {
        AccessDeniedException exception = Mockito.mock(AccessDeniedException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleGlobalException(exception, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertEquals(UNAUTHORIZED_USER, Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testHandleGlobalExceptionAccountStatusException() {
        AccountStatusException exception = Mockito.mock(AccountStatusException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleGlobalException(exception, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertEquals(ACCOUNT_LOCKED, Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testHandleGlobalExceptionBadCredentialsException() {
        BadCredentialsException exception = Mockito.mock(BadCredentialsException.class);
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleGlobalException(exception, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertEquals(USERNAME_PASSWORD_INCORRECT, Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testHandleGlobalExceptionNull() {
        ResponseEntity<ErrorDetailsResponse> response = globalExceptionHandler.handleGlobalException(null, webRequest);
        ErrorDetailsResponse responseBody = response.getBody();

        Assertions.assertEquals("", Objects.requireNonNull(responseBody).getMessage());
        Assertions.assertNull(responseBody.getPath());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
