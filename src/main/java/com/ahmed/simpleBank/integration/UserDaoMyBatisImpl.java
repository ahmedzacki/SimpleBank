// src/main/java/com/ahmed/simpleBank/integration/UserDaoMyBatisImpl.java
package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.integration.mapper.MyBatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserDaoMyBatisImpl implements UserDao {

    private final MyBatisMapper mapper;

    @Autowired
    public UserDaoMyBatisImpl(MyBatisMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<User> getAllUsers() {
        return mapper.queryForGetAllUsers();
    }

    @Override
    public User getUserById(UUID userId) {
        return mapper.queryForGetUserById(userId);
    }

    @Override
    public int insertUser(User user) {
        return mapper.queryForInsertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return mapper.queryForUpdateUser(user);
    }

    @Override
    public int deleteUser(UUID userId) {
        return mapper.queryForDeleteUserById(userId);
    }
}
