package com.max.crm.contoller;

import com.max.crm.base.BaseController;
import com.max.crm.base.ResultInfo;
import com.max.crm.model.TreeModel;
import com.max.crm.service.ModuleService;
import com.max.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @RequestMapping("/queryAllModules")
    @ResponseBody
    public List<TreeModel> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }


    @RequestMapping("/index")
    public String index(){
        return "module/module";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> moduleList(){
        return moduleService.moduleList();
    }

    @RequestMapping("/addModulePage")
    public String addModulePage(Integer grade, Integer parentId, Model model){
        model.addAttribute("grade",grade);
        model.addAttribute("parentId",parentId);
        return "module/add";
    }

    @RequestMapping("/updateModulePage")
    public String updateModulePage(Integer id,Model model){
        model.addAttribute("module",moduleService.selectByPrimaryKey(id));
        return "module/update";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo saveModule(Module module){
        moduleService.saveModule(module);
        return success("菜单添加成功！");
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultInfo updateModule(Module module){
        System.out.println(module);
        moduleService.updateModule(module);
        return success("菜单更新成功！");
    }

//    @RequestMapping("/queryAllModulesByGrade")
//    public List<Map<String,Object>> queryAllModulesByGrade(Integer grade){
//        return moduleService.queryAllModulesByGrade(grade);
//    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer mid){
        moduleService.deleteModuleById(mid);
        return success("菜单删除成功！");
    }

}
