package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    DatabaseRequestResult addUser(User user);
}
