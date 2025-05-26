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
      email, username, password,
      userRole, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled, createdAt
    FROM users
  """)
    List<User> queryForGetAllUsers();

    @Select("""
    SELECT
      userId, firstName, lastName,
      email, username, password,
      userRole, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled, createdAt
    FROM users
    WHERE userId = #{userId}
  """)
        // Tell MyBatis the name of this simple parameter
    User queryForGetUserById(@Param("userId") UUID userId);

    @Select("""
    SELECT
      userId, firstName, lastName,
      email, username, password,
      userRole, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled, createdAt
    FROM users
    WHERE email = #{email}
  """)
    User queryForGetUserByEmail(@Param("email") String email);

    @Insert("""
    INSERT INTO users (
      userId, firstName, lastName,
      email, username, password,
      userRole, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled, createdAt
    ) VALUES (
      #{userId}, #{firstName}, #{lastName},
      #{email}, #{username}, #{password},
      #{userRole}, #{isAccountNonExpired}, #{isAccountNonLocked}, #{isCredentialsNonExpired}, #{isEnabled}, #{createdAt}
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
      password     = #{password},
      userRole         = #{userRole},
      isAccountNonExpired = #{isAccountNonExpired},
      isAccountNonLocked = #{isAccountNonLocked},
      isCredentialsNonExpired = #{isCredentialsNonExpired},
      isEnabled = #{isEnabled}
    WHERE userId = #{userId}
  """)
    int queryForUpdateUser(User user);

    @Delete("DELETE FROM users WHERE userId = #{userId}")
    int queryForDeleteUserById(@Param("userId") UUID userId);


}


