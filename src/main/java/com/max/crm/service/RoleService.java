package com.max.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.crm.base.BaseService;
import com.max.crm.dao.ModuleMapper;
import com.max.crm.dao.PermissionMapper;
import com.max.crm.dao.RoleMapper;
import com.max.crm.query.RoleQuery;
import com.max.crm.utils.AssertUtil;
import com.max.crm.vo.Permission;
import com.max.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleMapper.queryAllRoles(userId);
    }


    public Map<String,Object> queryByParamsForTable(RoleQuery roleQuery){
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getLimit());
        PageInfo<Role> pageInfo=new PageInfo<>(roleMapper.selectByParams(roleQuery));
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role){
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名！");
        Role temp=roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp!=null,"该角色已存在！");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.insertSelective(role)<1,"角色记录添加失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role){
        AssertUtil.isTrue(role.getId()==null || roleMapper.selectByPrimaryKey(role.getId())==null,"待修改的记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名！");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp!=null && !(temp.getId().equals(role.getId())),"该角色已存在！");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"角色记录更新失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRole(Integer roleId){
        Role role = roleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(roleId==null || role==null,"待删除的记录不存在！");
        role.setIsValid(0);
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"角色更新失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer[] mIds,Integer roleId){
        Role role = roleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(roleId==null || role==null,"待授权的角色不存在");
        int count=permissionMapper.countPermissionByRoleId(roleId);
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionsByRoleId(roleId)<count,"权限分配失败！");
        }
        if(mIds!=null && mIds.length>0){
            List<Permission> permissions=new ArrayList<>();
            for(Integer mid:mIds){
                Permission permission = new Permission();
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permissions.add(permission);
            }
            permissionMapper.insertBatch(permissions);
        }



    }





}
