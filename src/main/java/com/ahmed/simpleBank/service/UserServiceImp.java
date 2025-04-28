package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;
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
            throw new SimpleBankDatabaseException("Database access error: " + dae.getMessage(), dae);
        }
    }


    @Override
    public DatabaseRequestResult addUser(User user) {
        try {
            int rowsAffected = dao.insertUser(user);
            DatabaseRequestResult result = new DatabaseRequestResult(rowsAffected);
            logger.debug("User inserted successfully, rows affected: {}", rowsAffected);
            return result;
        } catch (DuplicateKeyException dke) {
            logger.error("Duplicate key error while inserting user: {}", user.getEmail(), dke);
            throw new SimpleBankDatabaseException("Duplicate key: " + dke.getMessage(), dke);
        } catch (DataAccessException dae) {
            logger.error("Database access error while inserting user: {}", user.getEmail(), dae);
            throw new SimpleBankDatabaseException("Database access error: " + dae.getMessage(), dae);
        }
    }


}
