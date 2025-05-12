package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.Role;
import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;
import com.ahmed.simpleBank.dto.UserDTO;
import com.ahmed.simpleBank.exception.DatabaseException;
import com.ahmed.simpleBank.exception.DuplicateUserException;
import com.ahmed.simpleBank.integration.UserDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordHashingService passwordHashingService;

    @Autowired
    private Logger logger;

    // *************** User Methods *************************
    @Override
    public List<User> findAllUsers() {
        try {
            List<User> users = dao.getAllUsers();
            logger.debug("Retrieved users: {}", users);
            return users;
        } catch (DataAccessException dae) {
            logger.error("Database access error while retrieving users", dae);
            throw new DatabaseException("Database access error: " + dae.getMessage(), dae);
        }
    }


    @Override
    public DatabaseRequestResult addUser(UserDTO userData) {
        try {
            String hashedPassword = passwordHashingService.hashPassword(userData.getPassword());
            // Generate the username from the email (first part before the '@')
            String username = generateUsernameFromEmail(userData.getEmail());
            Role role = Role.fromString(userData.getRole());

            User user = new User(
                    userData.getFirstName(),
                    userData.getLastName(),
                    userData.getEmail(),
                    hashedPassword,
                    role,
                    username
            );

            int rowsAffected = dao.insertUser(user);

            DatabaseRequestResult result = new DatabaseRequestResult(rowsAffected);
            logger.debug("User inserted successfully, rows affected: {}", rowsAffected);
            return result;

        } catch (DuplicateKeyException dke) {
            logger.error("Duplicate key error while inserting user: {}", userData.getEmail(), dke);
            throw new DuplicateUserException(
                    "A user with email '" + userData.getEmail() + "' already exists"
            );
        } catch (DataAccessException dae) {
            logger.error("Database access error while inserting user: {}", userData.getEmail(), dae);
            throw new DatabaseException("Database access error: " + dae.getMessage(), dae);
        }
    }

    // *************** Helper Methods ************************* //
    private String generateUsernameFromEmail(String email) {
        return email.split("@")[0];
    }


}
