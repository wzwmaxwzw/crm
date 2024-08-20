package com.max.crm.contoller;

import com.max.crm.base.BaseController;
import com.max.crm.service.UserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user_role")
public class UserRoleController extends BaseController {

    @Resource
    private UserRoleService userRoleService;




}
