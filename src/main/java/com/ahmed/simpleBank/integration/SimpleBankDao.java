package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.User;

import java.util.List;

public interface SimpleBankDao {

    List<User> getAllUsers();
}
