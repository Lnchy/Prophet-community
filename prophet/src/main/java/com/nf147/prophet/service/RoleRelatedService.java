package com.nf147.prophet.service;

import com.nf147.prophet.entity.Role;
import com.nf147.prophet.entity.Rolepower;

import java.util.List;

public interface RoleRelatedService {

    //返回某个用户是否有某个角色
    boolean userIsRole(int userId, String roleName);

    //判断某个用户是否有某个权限
    boolean userHavePower(int userId, String powerName);

    //赋予某个用户某个角色
    boolean giveUserRole(int userId, int roleId);

    //取消某个用户的某个角色
    boolean cancelUserRole(int userId, int roleId);

    //查询所有角色
    List<Role> getAllRoles();

    //查询所有权限
    List<Rolepower> getAllPower();

    //给角色赋予权限
    boolean giveRolePower(int roleId, int powerId);

    //取消角色权限
    boolean cancelRolePower(int roleId, int powerId);

    //查询某个用户的角色
    List<Role> getUserRoles(int userId);

    //查询某个角色的所有权限
    List<Rolepower> getRolePower(int roleId);
}
