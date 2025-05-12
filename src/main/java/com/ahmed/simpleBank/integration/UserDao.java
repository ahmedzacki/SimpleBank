package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.User;
import java.util.List;
import java.util.UUID;

public interface UserDao {

    List<User> getAllUsers();                // Read All Users

    User getUserById(UUID userId);           // Read User by ID

    int insertUser(User user);               // Create

    int updateUser(User user);               // Update

    int deleteUser(UUID userId);             // Delete

}
