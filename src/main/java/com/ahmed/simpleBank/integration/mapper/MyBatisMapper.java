package com.ahmed.simpleBank.integration.mapper;

import com.ahmed.simpleBank.business.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MyBatisMapper {

    @Select("""
    SELECT
      userId, firstName, lastName,
      email, username, passwordHash,
      role, createdAt
    FROM users
  """)
    List<User> getAllUsers();

    @Select("""
    SELECT
      userId, firstName, lastName,
      email, username, passwordHash,
      role, createdAt
    FROM users
    WHERE userId = #{userId}
  """)
        // tell MyBatis the name of this simple parameter
    User getUserById(@Param("userId") UUID userId);

    @Insert("""
    INSERT INTO users (
      userId, firstName, lastName,
      email, username, passwordHash,
      role, createdAt
    ) VALUES (
      #{userId}, #{firstName}, #{lastName},
      #{email}, #{username}, #{passwordHash},
      #{role}, #{createdAt}
    )
  """)
    int insertUser(User user);

    @Update("""
    UPDATE users
    SET
      firstName    = #{firstName},
      lastName     = #{lastName},
      email        = #{email},
      username     = #{username},
      passwordHash = #{passwordHash},
      role         = #{role},
      createdAt    = #{createdAt}
    WHERE userId = #{userId}
  """)
    int updateUser(User user);

    @Delete("DELETE FROM users WHERE userId = #{userId}")
    int deleteUser(@Param("userId") UUID userId);
}


