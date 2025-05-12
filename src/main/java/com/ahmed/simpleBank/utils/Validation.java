package com.ahmed.simpleBank.utils;

import com.ahmed.simpleBank.dto.UserDTO;
import com.ahmed.simpleBank.exception.InvalidUserException;

public class Validation {

    /********************************** Utility Methods **********************************************/

    public static void validateDTOForCreate(UserDTO user){
        if (user == null ||
                isNullOrBlank(user.getFirstName()) ||
                isNullOrBlank(user.getLastName()) ||
                isNullOrBlank(user.getEmail()) ||
                isNullOrBlank(user.getPassword()) ||
                !user.getEmail().contains("@") ||
                isNullOrBlank(user.getRole())) {
            throw new InvalidUserException("User is not fully populated");
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }
    
}
