package com.max.crm.dao;

import com.max.crm.base.BaseMapper;
import com.max.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    public Integer countUserRoleByUserId(Integer userId);

    public Integer deleteUserRoleByUserId(Integer userId);

}