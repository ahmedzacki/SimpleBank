package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.exception.DuplicateUserException;
import com.ahmed.simpleBank.exception.InvalidUserException;
import com.ahmed.simpleBank.exception.DatabaseException;
import com.ahmed.simpleBank.dto.ErrorResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private Logger logger;

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDup(DuplicateUserException ex) {
        ErrorResponse body = new ErrorResponse("USER_EXISTS", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> handleInvalid(InvalidUserException ex) {
        logger.warn(ex.getMessage());
        ErrorResponse body = new ErrorResponse("INVALID_INPUT", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException ex) {
        ErrorResponse body = new ErrorResponse(
                "DATABASE_ERROR",
                ex.getMessage() != null ? ex.getMessage() : "An unexpected database error occurred"
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        ErrorResponse body = new ErrorResponse("INTERNAL_ERROR", "Something went wrong");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
