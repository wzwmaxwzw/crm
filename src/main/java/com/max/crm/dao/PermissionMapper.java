package com.max.crm.dao;

import com.max.crm.base.BaseMapper;
import com.max.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    public Integer countPermissionByRoleId(Integer roleId);

    public Integer deletePermissionsByRoleId(Integer roleId);

    public List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    public List<String> queryUserHasRolesHasPermissions(Integer userId);

    public Integer countPermissionsByModuleId(Integer mid);

    public Integer deletePermissionsByModuleId(Integer mid);

}