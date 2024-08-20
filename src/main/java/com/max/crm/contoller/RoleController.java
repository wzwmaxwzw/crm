package com.max.crm.contoller;

import com.max.crm.base.BaseController;
import com.max.crm.base.ResultInfo;
import com.max.crm.query.RoleQuery;
import com.max.crm.service.RoleService;
import com.max.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @RequestMapping("/queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleService.queryAllRoles(userId);
    }

    @RequestMapping("/index")
    public String index(){
        return "role/role";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> roleList(RoleQuery roleQuery){
        return roleService.queryByParamsForTable(roleQuery);
    }


    @RequestMapping("/addOrUpdateRolePage")
    public String addRolePage(Integer id, Model model){
        if(id!=null){
            model.addAttribute("role",roleService.selectByPrimaryKey(id));
        }
        return "role/add_update";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo saveRole(Role role){
        roleService.saveRole(role);
        return success("角色记录添加成功！");
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("角色记录更新成功！");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer roleId){
        roleService.deleteRole(roleId);
        return success("角色记录删除成功！");
    }

    @RequestMapping("/toAddGrantPage")
    public String toAddGrantPage(Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";

    }

    @RequestMapping("/addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer[] mids,Integer roleId){
        roleService.addGrant(mids,roleId);
        return success("权限管理添加成功！");
    }





}
