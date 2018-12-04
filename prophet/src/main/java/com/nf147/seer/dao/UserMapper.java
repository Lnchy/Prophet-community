package com.nf147.seer.dao;

import com.nf147.seer.entity.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByEmail(String email);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int changePwd(Integer userId, String newPwd);
}