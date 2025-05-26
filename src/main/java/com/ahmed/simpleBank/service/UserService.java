package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> findAllUsers();
    User findUserById(UUID id);
    Optional<User> findUserByEmail(String email);
    DatabaseRequestResult deleteUser(UUID id);
    DatabaseRequestResult updateUser(User user);
}
