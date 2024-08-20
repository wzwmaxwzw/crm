package com.max.crm.dao;

import com.max.crm.base.BaseMapper;
import com.max.crm.vo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {

    User  queryUserByUserName(String userName);

    List<Map<String,Object>> queryAllSales();

}