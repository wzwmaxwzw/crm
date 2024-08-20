package com.max.crm.contoller;

import com.max.crm.base.BaseController;
import com.max.crm.service.PermissionService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class PermissionController extends BaseController {

    @Resource
    private PermissionService permissionService;


}
