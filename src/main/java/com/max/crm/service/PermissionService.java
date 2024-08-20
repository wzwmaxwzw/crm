package com.max.crm.service;

import com.max.crm.base.BaseService;
import com.max.crm.dao.PermissionMapper;
import com.max.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    public List<String> queryUserHasRolesHasPermissions(Integer userId){
        return permissionMapper.queryUserHasRolesHasPermissions(userId);
    }


}
