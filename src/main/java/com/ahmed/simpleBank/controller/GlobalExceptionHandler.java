package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.service.SimpleBankDatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String DB_ERROR_MSG =
            "Error communicating with the database";

    @Autowired
    Logger logger;

    @ExceptionHandler(value = {SimpleBankDatabaseException.class})
    protected ResponseEntity<Object> handleInvalidDataException(
            RuntimeException ex, WebRequest request
    ) {
        logger.error(DB_ERROR_MSG, ex);
        return new ResponseEntity<>(DB_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT,
    reason = "Data integrity violation")
    @ExceptionHandler(value = {DuplicateKeyException.class})
    public void conflictHandler(DuplicateKeyException ex) {
        logger.error(DB_ERROR_MSG, ex);
    }
}
