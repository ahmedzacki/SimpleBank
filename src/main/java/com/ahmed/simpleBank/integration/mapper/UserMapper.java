package com.ahmed.simpleBank.integration.mapper;

import com.ahmed.simpleBank.business.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface UserMapper {

    @Select("""
    SELECT
      userId, firstName, lastName,
      email, username, passwordHash,
      role, createdAt
    FROM users
  """)
    List<User> queryForGetAllUsers();

    @Select("""
    SELECT
      userId, firstName, lastName,
      email, username, passwordHash,
      role, createdAt
    FROM users
    WHERE userId = #{userId}
  """)
        // Tell MyBatis the name of this simple parameter
    User queryForGetUserById(@Param("userId") UUID userId);

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
    int queryForInsertUser(User user);

    @Update("""
    UPDATE users
    SET
      firstName    = #{firstName},
      lastName     = #{lastName},
      email        = #{email},
      username     = #{username},
      passwordHash = #{passwordHash},
      role         = #{role}
    WHERE userId = #{userId}
  """)
    int queryForUpdateUser(User user);

    @Delete("DELETE FROM users WHERE userId = #{userId}")
    int queryForDeleteUserById(@Param("userId") UUID userId);


}


