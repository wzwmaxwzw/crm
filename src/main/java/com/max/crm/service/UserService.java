package com.max.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.crm.base.BaseService;
import com.max.crm.dao.RoleMapper;
import com.max.crm.dao.UserMapper;
import com.max.crm.dao.UserRoleMapper;
import com.max.crm.model.UserModel;
import com.max.crm.query.UserQuery;
import com.max.crm.utils.AssertUtil;
import com.max.crm.utils.Md5Util;
import com.max.crm.utils.PhoneUtil;
import com.max.crm.utils.UserIDBase64;
import com.max.crm.vo.User;
import com.max.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.*;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;


    public UserModel userLogin(String userName,String userPwd){
        checkLoginParams(userName,userPwd);
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(user==null,"用户不存在或者已经注销！");
        checkLoginPwd(userPwd,user.getUserPwd());
        return buildUserInfo(user);
    }

    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    private void checkLoginPwd(String userPwd, String password) {
        userPwd= Md5Util.encode(userPwd);
        AssertUtil.isTrue(!userPwd.equals(password),"用户的密码不正确！");
    }

    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"密码不能为空！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        User user = userMapper.selectByPrimaryKey(userId);
        checkPasswordParams(user,oldPassword,newPassword,confirmPassword);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"用户密码更新失败！");

    }


    private void checkPasswordParams(User user, String oldPassword, String newPassword, String confirmPassword) {
        AssertUtil.isTrue(user==null,"用户未登录或者不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原始密码！");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))),"原始的密码不正确！");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码！");
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码和原始的密码不能相同！");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请输入确认密码！");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码与确认密码不相同！");
    }

    public List<Map<String,Object>> queryAllSales(){
        return userMapper.queryAllSales();
    }


    public Map<String,Object> queryUserByParams(UserQuery query){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<User> pageInfo=new PageInfo<>(userMapper.selectByParams(query));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user){
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertSelective(user)<1,"用户添加失败！");

        relationUserRole(user.getId(),user.getRoleIds());


    }

    private void relationUserRole(Integer userId, String roleIds) {
        int count = userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配失败！");
        }
        if(!StringUtils.isBlank(roleIds)){
            List<UserRole> userRoles=new ArrayList<>();
            for(String s:roleIds.split(",")){
                UserRole userRole=new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(Integer.parseInt(s));
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            }
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles)<userRoles.size(),"用户角色分配失败！");
        }

    }


    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(user!=null,"该用户已经存在！");
        AssertUtil.isTrue(StringUtils.isBlank(email),"请输入邮箱地址！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号格式不正确");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){
        User temp = userMapper.selectByPrimaryKey(user.getId());
        AssertUtil.isTrue(temp==null,"待更新记录不存在！");
        checkParams(user.getUserName(),user.getEmail(),user.getPhone(),user.getId());
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"用户更新失败！");

        relationUserRole(user.getId(),user.getRoleIds());

    }

    public void checkParams(String userName, String email, String phone, Integer userId){
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(user!=null && !(user.getId().equals(userId)),"该用户已存在！");
        AssertUtil.isTrue(StringUtils.isBlank(email),"请输入邮箱地址！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号码格式不正确");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserByIds(Integer[] ids){
        AssertUtil.isTrue(ids==null || ids.length==0,"请输入待删除的用户记录！");
        AssertUtil.isTrue(userMapper.deleteBatch(ids)!=ids.length,"用户记录删除失败！");

        for(Integer userId:ids){
            Integer count = userRoleMapper.countUserRoleByUserId(userId);
            if(count>0){
                AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"删除用户角色失败！");
            }
        }
    }



}
