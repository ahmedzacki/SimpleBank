package com.ahmed.simpleBank.integration.mapper;

import com.ahmed.simpleBank.business.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SimpleBankMapper {
    List<User> getAllUsers();
}
