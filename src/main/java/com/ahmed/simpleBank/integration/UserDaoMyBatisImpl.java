package com.ahmed.simpleBank.integration;


import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.integration.mapper.MyBatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoMyBatisImpl implements UserDao {

    @Autowired
    private MyBatisMapper mapper;

    @Override
    public List<User> getAllUsers() {
        return mapper.getAllUsers();
    }

    @Override
    public User getUserById(int userId) {
        return mapper.getUserById(userId);
    }

    @Override
    public int insertUser(User user) {
        return mapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        mapper.updateUser(user);
    }

    @Override
    public void deleteUser(int userId) {
        mapper.deleteUser(userId);
    }
}
