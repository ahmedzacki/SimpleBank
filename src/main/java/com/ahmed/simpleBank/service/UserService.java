package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.integration.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao dao;
    @Autowired
    private Logger logger;


    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            users = dao.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }


    public void addUser(User user) {
        try {
            dao.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
