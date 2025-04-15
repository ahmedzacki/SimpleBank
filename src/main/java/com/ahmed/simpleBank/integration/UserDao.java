package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();  // Read All Users

    User getUserById(int userId);  // Read User by ID

    void insertUser(User user);  // Create

    void updateUser(User user);  // Update

    void deleteUser(int userId);  // Delete

}
