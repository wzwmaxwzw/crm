package com.max.crm.contoller;

import com.max.crm.base.BaseController;
import com.max.crm.base.ResultInfo;
import com.max.crm.dao.UserMapper;
import com.max.crm.exceptions.ParamsException;
import com.max.crm.model.UserModel;
import com.max.crm.query.UserQuery;
import com.max.crm.service.UserService;
import com.max.crm.utils.LoginUserUtil;
import com.max.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @PostMapping ("/login")
    @ResponseBody
    public ResultInfo userLogin(String userName,String userPwd){
        ResultInfo resultInfo=new ResultInfo();
        UserModel userModel = userService.userLogin(userName, userPwd);
        resultInfo.setResult(userModel);
        return resultInfo;
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,String oldPassword,String newPassword,
                                         String confirmPassword){
        ResultInfo resultInfo=new ResultInfo();
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updateUserPassword(userId,oldPassword,newPassword,confirmPassword);
        return resultInfo;
    }


    @RequestMapping("/toPasswordPage")
    public String toPasswordPage(){
        return "/user/password";
    }


    @RequestMapping("/queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        return userService.queryAllSales();
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> queryUserByParams(UserQuery query){
        return userService.queryUserByParams(query);
    }

    @RequestMapping("/index")
    public String index(){
        return "user/user";
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo saveUser(User user){
        userService.saveUser(user);
        return success("用户添加成功！");
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户更新成功！");
    }

    @RequestMapping("/addOrUpdateUserPage")
    public String addUserPage(Integer id, Model model){
        if(id!=null){
            model.addAttribute("user1",userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUserByIds(ids);
        return success("用户记录删除成功！");
    }



}
