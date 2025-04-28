package com.ahmed.simpleBank.integration.mapper;

import com.ahmed.simpleBank.business.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyBatisMapper {
    List<User> getAllUsers();

    int insertUser(User user);

    User getUserById(int userId);

    void updateUser(User user);

    void deleteUser(int userId);
}
