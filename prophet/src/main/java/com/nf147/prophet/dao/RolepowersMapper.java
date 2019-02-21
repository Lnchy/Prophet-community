package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Rolepowers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolepowersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rolepowers record);

    Rolepowers selectByPrimaryKey(Integer id);

    List<Rolepowers> selectAll();

    int updateByPrimaryKey(Rolepowers record);

    // 根据角色id和权限id查询
    Rolepowers selectByRoleIdAndPowerId(@Param("roleId") int roleId,@Param("powerId") int powerId);

}