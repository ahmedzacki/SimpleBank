package com.ahmed.simpleBank.utils;

import com.ahmed.simpleBank.dto.UserDTO;
import com.ahmed.simpleBank.exception.InvalidUserException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Validation {

    /********************************** Utility Methods **********************************************/

    public static void validateUserDTO(UserDTO user){
        if (user == null ||
                isNullOrBlank(user.getFirstName()) ||
                isNullOrBlank(user.getLastName()) ||
                isNullOrBlank(user.getEmail()) ||
                isNullOrBlank(user.getPassword()) ||
                !user.getEmail().contains("@")) {
            throw new InvalidUserException("User is not fully populated");
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }
}
