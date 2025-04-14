package com.ahmed.simpleBank.integration;


import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.integration.mapper.SimpleBankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("simpleBankBao")
public class SimpleBankMyBatisImpl implements SimpleBankDao{

    @Autowired
    private SimpleBankMapper mapper;


    @Override
    public List<User> getAllUsers() {
        List<User> res = mapper.getAllUsers();
        return res;
    }

    @Override
    public void insertUser(User user) {
        mapper.insertUser(user);
    }
}
