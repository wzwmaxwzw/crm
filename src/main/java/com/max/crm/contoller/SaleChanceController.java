package com.max.crm.contoller;

import com.max.crm.annotation.RequirePermission;
import com.max.crm.base.BaseController;
import com.max.crm.base.ResultInfo;
import com.max.crm.query.SaleChanceQuery;
import com.max.crm.service.SaleChanceService;
import com.max.crm.utils.CookieUtil;
import com.max.crm.utils.LoginUserUtil;
import com.max.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;



    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery query,Integer flag,HttpServletRequest request){
        if(flag!=null && flag==1){
            int userId = LoginUserUtil.releaseUserIdFromCookie(request);
            query.setAssignMan(userId);
        }
        return saleChanceService.querySaleChanceByParams(query);
    }

    @RequestMapping("/index")
    public String index(){
        return "/saleChance/sale_chance";
    }

    @RequirePermission(code = "101002")
    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo saveSaleChance(HttpServletRequest request,SaleChance saleChance){
        String userName = CookieUtil.getCookieValue(request, "userName");
        saleChance.setCreateMan(userName);
        saleChanceService.saveSalChance(saleChance);
        return success("营销机会数据添加成功！");

    }

    @RequestMapping("/addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id,HttpServletRequest request){
        if(id!=null){
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            request.setAttribute("saleChance",saleChance);
        }
        return "/saleChance/add_update";
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功！");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChance(ids);
        return success("营销机会数据删除成功！");
    }

    @RequestMapping("/updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("开发状态更新成功！");
    }


}
