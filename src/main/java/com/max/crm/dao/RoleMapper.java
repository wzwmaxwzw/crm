package com.max.crm.dao;

import com.max.crm.base.BaseMapper;
import com.max.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    public List<Map<String,Object>> queryAllRoles(Integer userId);

    public Role queryRoleByRoleName(String roleName);




}