package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Role;
import com.nf147.prophet.entity.Rolesusers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolesusersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rolesusers record);

    Rolesusers selectByPrimaryKey(Integer id);

    List<Rolesusers> selectAll();

    int updateByPrimaryKey(Rolesusers record);

    //根据用户id和角色id查找
    Rolesusers selectByUserIdAndRoleId(@Param("userId")int userId, @Param("roleId") int roleId);

    //根据用户id 和角色id删除
    int deleteByUserIdAndRoleId(@Param("userId")int userId, @Param("roleId") int roleId);
}