package com.ahmed.simpleBank.utils;

import com.ahmed.simpleBank.dto.UserDTO;

public class Validation {

    /********************************** Utility Methods **********************************************/

    public static void validateUserDTO(UserDTO user){
        if (user == null ||
                isNullOrBlank(user.getFirstName()) ||
                isNullOrBlank(user.getLastName()) ||
                isNullOrBlank(user.getEmail()) ||
                isNullOrBlank(user.getPassword())) {
            throw new IllegalArgumentException("User is not fully populated");
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }
}
