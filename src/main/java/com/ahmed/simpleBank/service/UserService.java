package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;
import com.ahmed.simpleBank.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    DatabaseRequestResult addUser(UserDTO user);
}
