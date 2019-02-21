package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    //查询角色
    Role selectByRoleName(@Param("roleName")String roleName);

    //查询某个用户的所有角色
    List<Role> selectRoleByUserId(@Param("userId")int userId);
}