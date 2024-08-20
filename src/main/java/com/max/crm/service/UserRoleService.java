package com.max.crm.service;

import com.max.crm.base.BaseService;
import com.max.crm.dao.UserRoleMapper;
import com.max.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserRoleService extends BaseService<UserRole,Integer> {

    @Resource
    private UserRoleMapper userRoleMapper;


}
