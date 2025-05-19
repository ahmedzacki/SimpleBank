package com.ahmed.simpleBank.exception;

import com.ahmed.simpleBank.dto.ErrorResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private Logger logger;

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDup(DuplicateUserException ex) {
        ErrorResponse body = new ErrorResponse("USER_EXISTS", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }

    @ExceptionHandler(InvalidUserInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalid(InvalidUserInputException ex) {
        logger.warn(ex.getMessage());
        ErrorResponse body = new ErrorResponse("INVALID_INPUT", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException ex) {
        ErrorResponse body = new ErrorResponse(
                "DATABASE_ERROR",
                ex.getMessage() != null ? ex.getMessage() : "An unexpected database error occurred, please try again"
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(UserNotFoundException ex) {
        ErrorResponse body = new ErrorResponse(
                "USER_NOT_FOUND_ERROR",
                ex.getMessage() != null ? ex.getMessage() : "user not found in the database"
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Only for UUID mismatches; otherwise rethrow or let fall through
        if (ex.getRequiredType() == UUID.class) {
            ErrorResponse body = new ErrorResponse(
                    "INVALID_ID",
                    "User ID must be a valid UUID, but got '" + ex.getValue() + "'"
            );
            logger.error("Received INVALID USER ID: '{}'", ex.getValue());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(body);
        }
        // for other mismatches, fall through to the generic handler
        throw ex;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleEmptyBody(HttpMessageNotReadableException ex) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_REQUEST",
                "Request body is missing or malformed JSON");
        logger.error("Received error: {}", body.toString());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }
    
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {
        ErrorResponse body = new ErrorResponse(
                "ACCOUNT_NOT_FOUND_ERROR",
                ex.getMessage() != null ? ex.getMessage() : "Account not found in the database");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }
    
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        ErrorResponse body = new ErrorResponse(
                "INSUFFICIENT_FUNDS",
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        ErrorResponse body = new ErrorResponse("INTERNAL_ERROR", "Something went wrong");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
