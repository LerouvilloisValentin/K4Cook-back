package fr.k4cook.controllers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class managing all expected Exception in Controllers
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Exception Handler for IllegalArgument
     * 
     * @param illegalArgumentException exception
     * @return Bad Requestion response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity.badRequest().body(illegalArgumentException.getMessage());
    }

    /**
     * Exception Handler for NoSuchElement
     * 
     * @param noSuchElementException exception
     * @return Not found response entity
     */
    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<String> noSuchElementExceptionHandler(NoSuchElementException noSuchElementException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSuchElementException.getMessage());
    }

    /**
     * Exception Handler for AuthenticationServiceException
     * 
     * @param authenticationServiceException exception
     * @return Unauthoried response entity
     */
    @ExceptionHandler(AuthenticationServiceException.class)
    private ResponseEntity<String> authenticationServiceException(
            AuthenticationServiceException authenticationServiceException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationServiceException.getMessage());
    }
}
