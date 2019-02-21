package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Rolepower;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolepowerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rolepower record);

    Rolepower selectByPrimaryKey(Integer id);

    List<Rolepower> selectAll();

    int updateByPrimaryKey(Rolepower record);

    //获取一个角色所有权限
    List<Rolepower> selectPowerByRoleId(@Param("roleId")int roleId);
}