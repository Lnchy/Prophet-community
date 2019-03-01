package com.nf147.prophet.dao;

import com.nf147.prophet.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByEmail(String email);

    User selectByName(String userName);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int upUserInfo(User user);

    int changePwd(Integer userId, String newPwd);

    //更改背景图片
    int changeBgImg(@Param("userId") int userId, @Param("imgPath") String imgPath);
}