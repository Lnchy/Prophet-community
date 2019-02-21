package com.nf147.prophet.service.Impl;

import com.nf147.prophet.dao.RoleMapper;
import com.nf147.prophet.dao.RolepowerMapper;
import com.nf147.prophet.dao.RolepowersMapper;
import com.nf147.prophet.dao.RolesusersMapper;
import com.nf147.prophet.entity.Role;
import com.nf147.prophet.entity.Rolepower;
import com.nf147.prophet.entity.Rolepowers;
import com.nf147.prophet.entity.Rolesusers;
import com.nf147.prophet.service.RoleRelatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleRelatedServiceImpl implements RoleRelatedService {
    @Autowired      //角色表
            RoleMapper roleMapper;

    @Autowired      //用户角色关联表
            RolesusersMapper rolesusersMapper;

    @Autowired      //权限表
            RolepowerMapper rolepowerMapper;

    @Autowired      //角色权限关联表
            RolepowersMapper rolepowersMapper;

    //返回某个用户是否有某个角色
    @Override
    public boolean userIsRole(int userId, String roleName) {
        Role role = roleMapper.selectByRoleName(roleName);

        if (role != null) {
            return rolesusersMapper.selectByUserIdAndRoleId(userId, role.getId()) != null;
        } else {
            return false;
        }
    }

    //判断某个用户是否有某个权限
    @Override
    public boolean userHavePower(int userId, String powerName) {
        List<Role> userRoles = getUserRoles(userId);

        for (Role userRole : userRoles) {
            List<Rolepower> rolePower = getRolePower(userRole.getId());
            for (Rolepower power : rolePower) {
                if (power.getName().equals(powerName)) {
                    return true;
                }
            }
        }
        return false;
    }

    //赋予某个用户某个角色
    @Override
    public boolean giveUserRole(int userId, int roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        String roleName = null;
        if (null != role) {
            roleName = role.getName();
        } else {
            return false;
        }
        // 有这个角色直接返回真
        if (userIsRole(userId, roleName)) {
            return true;
        } else {
            // 赋予角色
            Rolesusers rolesusers = new Rolesusers();
            rolesusers.setUserId(userId);
            rolesusers.setRoleId(roleId);

            return rolesusersMapper.insert(rolesusers) > 0;
        }
    }

    //取消某个用户的某个角色
    @Override
    public boolean cancelUserRole(int userId, int roleId) {
        List<Role> allRoles = getAllRoles();
        for (Role role : allRoles) {
            if (role.getId() == roleId) {
                if (userIsRole(userId, role.getName())) {
                    //删除
                    return rolesusersMapper.deleteByUserIdAndRoleId(userId, roleId) > 0;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    //查询所有角色
    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectAll();
    }

    //查询所有权限
    @Override
    public List<Rolepower> getAllPower() {
        List<Role> allRoles = getAllRoles();
        List<Rolepower> allPower = new ArrayList<>();

        for (Role role : allRoles) {
            List<Rolepower> rolePower = getRolePower(role.getId());
            allPower.addAll(rolePower);
        }

        return allPower;
    }

    //给角色赋予权限
    @Override
    public boolean giveRolePower(int roleId, int powerId) {
        Rolepowers power = rolepowersMapper.selectByRoleIdAndPowerId(roleId, powerId);
        if (null == power) {
            //无该权限
            Rolepowers rolepowers = new Rolepowers();
            rolepowers.setRoleId(roleId);
            rolepowers.setPowerId(powerId);

            return rolepowersMapper.insert(rolepowers) > 0;
        } else {
            return true;
        }
    }

    //取消角色权限
    @Override
    public boolean cancelRolePower(int roleId, int powerId) {
        Rolepowers power = rolepowersMapper.selectByRoleIdAndPowerId(roleId, powerId);
        if (null == power) {
            return true;
        } else {
            return rolepowersMapper.deleteByPrimaryKey(power.getId()) > 0;
        }
    }

    //查询某个用户的角色
    @Override
    public List<Role> getUserRoles(int userId) {
        return roleMapper.selectRoleByUserId(userId);
    }

    //查询某个角色的所有权限
    @Override
    public List<Rolepower> getRolePower(int roleId) {
        return rolepowerMapper.selectPowerByRoleId(roleId);
    }
}
