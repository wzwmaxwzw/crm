package com.max.crm.contoller;

import com.github.pagehelper.PageInfo;
import com.max.crm.base.BaseController;
import com.max.crm.base.ResultInfo;
import com.max.crm.query.CusDevPlanQuery;
import com.max.crm.service.CusDevPlanService;
import com.max.crm.service.SaleChanceService;
import com.max.crm.vo.CusDevPlan;
import com.max.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.Map;

@Controller
@RequestMapping("/cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("/index")
    public String index(){
        return "/cusDevPlan/cus_dev_plan";
    }

    @RequestMapping("/toCusDevPlanDataPage")
    public String toCusDevPlanDataPage(Model model,Integer sid){
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(sid);
        model.addAttribute("saleChance",saleChance);
        return "/cusDevPlan/cus_dev_plan_data";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlanByParams(CusDevPlanQuery query){
        return cusDevPlanService.queryCusDevPlansByParams(query);
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo saveCusDevPlan(CusDevPlan cusDevPlan){
        System.out.println(cusDevPlan);
        cusDevPlanService.saveCusDevPlan(cusDevPlan);
        return success("计划项目添加成功！");
    }

    @RequestMapping("/addOrUpdateCusDevPlanPage")
    public String addOrUpdateCusDevPlanPage(Integer sid,Integer id,Model model){
        if(id!=null){
            model.addAttribute("cusDevPlan",cusDevPlanService.selectByPrimaryKey(id));
        }
        model.addAttribute("sid",sid);
        return "cusDevPlan/add_update";

    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划更新成功！");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return success("计划项目删除成功！");
    }


}
