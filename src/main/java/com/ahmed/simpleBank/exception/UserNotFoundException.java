package com.ahmed.simpleBank.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("User “" + id + "” not found");
    }
}
