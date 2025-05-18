package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.Role;
import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;
import com.ahmed.simpleBank.dto.UserDTO;
import com.ahmed.simpleBank.exception.DatabaseException;
import com.ahmed.simpleBank.exception.DuplicateUserException;
import com.ahmed.simpleBank.exception.UserNotFoundException;
import com.ahmed.simpleBank.integration.UserDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private Logger logger;

    private final UserDao dao;
    private final PasswordHashingService passwordHashingService;

    public UserServiceImp(UserDao dao, PasswordHashingService passwordHashingService) {
        this.dao = dao;
        this.passwordHashingService = passwordHashingService;
    }

    // *************** User Methods *************************
    @Override
    public List<User> findAllUsers() {
        try {
            List<User> users = dao.getAllUsers();
            logger.debug("Retrieved users: {}", users);
            return users;
        } catch (DataAccessException dae) {
            logger.error("Database access error while retrieving users", dae);
            throw new DatabaseException("Error occurred while accessing database, please try again.", dae);
        }
    }
    
    @Override
    @Transactional
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
                    "A user with email '" + userData.getEmail() + "' already exists", dke
            );
        } catch (DataAccessException dae) {
            logger.error("Database access error while inserting user: {}", userData.getEmail(), dae);
            throw new DatabaseException("Database access error: ", dae);
        }
    }

    @Override
    public User findUserById(UUID id) {
        try {
            User user = dao.getUserById(id);
            if (user == null) {
                throw new UserNotFoundException(id);
            }
            logger.debug("Retrieved user: {} from the database", user);
            return user;
        } catch (DataAccessException dae) {
            logger.error("Database access error while retrieving user", dae);
            throw new DatabaseException("Error occurred while accessing database, please try again.", dae);
        }
    }

    @Override
    @Transactional
    public DatabaseRequestResult updateUser(UserDTO userDTO) {
        User existing = dao.getUserById(userDTO.getUserId());
        if (existing == null) {
            throw new UserNotFoundException(userDTO.getUserId());
        }

        // Merging in changes
        if (userDTO.getFirstName() != null) {
            existing.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            existing.setLastName(userDTO.getLastName());
        }
        if (userDTO.getEmail() != null) {
            existing.setEmail(userDTO.getEmail());
        }
        if (userDTO.getRole() != null) {
            existing.setRole(Role.fromString(userDTO.getRole()));
        }
        if (userDTO.getPassword() != null) {
            // re-hashing only if a new password was provided
            String hashed = passwordHashingService.hashPassword(userDTO.getPassword());
            existing.setPasswordHash(hashed);
        }

        try {
            int rows = dao.updateUser(existing);
            logger.debug("updated user info with userId: {}", existing.getUserId());
            return new DatabaseRequestResult(rows);
        } catch (DuplicateKeyException dke) {
            // e.g. email or username conflict
            throw new DuplicateUserException(
                    "A user with that unique field already exists", dke
            );
        } catch (DataAccessException dae) {
            logger.error("Database error while updating user {}", userDTO.getUserId(), dae);
            throw new DatabaseException(
                    "Error occurred while updating user, please try again.", dae
            );
        }
    }

    @Override
    @Transactional
    public DatabaseRequestResult deleteUser(UUID id) {
        try {
            int rowsAffected = dao.deleteUser(id);
            if (rowsAffected == 0) {
                logger.debug("User {} was not deleted because it was not found", id);
                throw new UserNotFoundException(id);
            }
            DatabaseRequestResult result = new DatabaseRequestResult(rowsAffected);
            logger.debug("User deleted from database successfully, user ID: {}", id);
            return result;
        } catch (DataAccessException dae) {
            logger.error("Database access error while deleting user by id: {}", id, dae);
            throw new DatabaseException("Database access error: ", dae);
        }
    }

    // *************** Helper Methods ************************* //
    private String generateUsernameFromEmail(String email) {
        return email.split("@")[0];
    }


}
