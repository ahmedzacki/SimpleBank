package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.business.UserRole;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;
import com.ahmed.simpleBank.exception.DatabaseException;
import com.ahmed.simpleBank.exception.DuplicateUserException;
import com.ahmed.simpleBank.exception.UserNotFoundException;
import com.ahmed.simpleBank.integration.UserDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private Logger logger;

    private final UserDao dao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao dao, BCryptPasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
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
    public User findUserById(UUID id) {
        try {
            User user = dao.getUserById(id);
            logger.debug("Retrieved user: {}", user);
            return user;
        } catch (DataAccessException dae) {
            logger.error("Database access error while retrieving user by id: {}", id, dae);
            throw new DatabaseException("Error occurred while accessing database, please try again.", dae);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        try {
            User user = dao.getUserByEmail(email);
            logger.debug("Retrieved user: {}", user);
            return Optional.ofNullable(user);
        } catch (DataAccessException dae) {
            logger.error("Database access error while retrieving user by email: {}", email, dae);
            throw new DatabaseException("Error occurred while accessing database, please try again.", dae);
        }
    }

    @Override
    @Transactional
    public DatabaseRequestResult updateUser(User user) {
        User existing = dao.getUserById(user.getUserId());
        if (existing == null) {
            throw new UserNotFoundException(user.getUserId());
        }

        // Merging in changes
        if (user.getFirstName() != null) {
            existing.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existing.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            existing.setEmail(user.getEmail());
        }
        if (user.getUserRole() != null) {
            existing.setUserRole(UserRole.valueOf(user.getUserRole().toString()));
        }
        if (user.getPassword() != null) {
            String hashed = passwordEncoder.encode(user.getPassword());
            existing.setPassword(hashed);
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
            logger.error("Database error while updating user {}", user.getUserId(), dae);
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


}
