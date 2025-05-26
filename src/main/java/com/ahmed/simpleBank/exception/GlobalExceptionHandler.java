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

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.security.authentication.LockedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.DataAccessException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

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
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransaction(InvalidTransactionException ex) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_TRANSACTION",
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(InvalidTransactionTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransactionType(InvalidTransactionTypeException ex) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_TRANSACTION_TYPE",
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAmount(InvalidAmountException ex) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_AMOUNT",   
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        ErrorResponse body = new ErrorResponse(
                "JWT_AUTHENTICATION_ERROR",
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorResponse body = new ErrorResponse(
                "JWT_EXPIRED",
                "The authentication token has expired"
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_CREDENTIALS",
                "The username or password is incorrect"
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorResponse> handleAccountStatus(LockedException ex) {
        ErrorResponse body = new ErrorResponse(
                "ACCOUNT_LOCKED",
                "The account is locked"
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponse body = new ErrorResponse(
                "ACCESS_DENIED",
                "You are not authorized to access this resource"
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(body);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtSignature(SecurityException ex) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_JWT",
                "The JWT signature is invalid"
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKey(DuplicateKeyException ex) {
        ErrorResponse body = new ErrorResponse(
                "USER_EXISTS",
                "A user with this email already exists"
        );
        logger.error("Duplicate key error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccess(DataAccessException ex) {
        ErrorResponse body = new ErrorResponse(
                "DATABASE_ERROR",
                "An unexpected database error occurred"
        );
        logger.error("Database access error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler({
            JwtException.class,
            MalformedJwtException.class,
            UnsupportedJwtException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> handleJwtValidationExceptions(Exception ex) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_JWT",
                "Invalid authentication token"
        );
        logger.error("JWT Validation error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
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
