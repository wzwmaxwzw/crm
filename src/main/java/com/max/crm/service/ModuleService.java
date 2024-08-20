package com.max.crm.service;

import com.max.crm.base.BaseService;
import com.max.crm.dao.ModuleMapper;
import com.max.crm.dao.PermissionMapper;
import com.max.crm.model.TreeModel;
import com.max.crm.utils.AssertUtil;
import com.max.crm.vo.Module;
import com.max.crm.vo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {

    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    public List<TreeModel> queryAllModules(Integer roleId){
        List<TreeModel> treeModels = moduleMapper.queryAllModules();
        List<Integer> roleHasMIds=permissionMapper.queryRoleHasAllModuleIdsByRoleId(roleId);

        if(roleHasMIds!=null && roleHasMIds.size()>0){
            treeModels.forEach(treeModel -> {
                if(roleHasMIds.contains(treeModel.getId())){
                    treeModel.setChecked(true);
                }
            });
        }

      return treeModels;

    }

    public Map<String,Object> moduleList(){
        Map<String,Object> map=new HashMap<>();
        List<Module> modules=moduleMapper.queryModules();
        map.put("code",0);
        map.put("msg","");
        map.put("count",modules.size());
        map.put("data",modules);

        return map;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveModule(Module module){
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"请输入菜单名！");
        Integer grade = module.getGrade();
        AssertUtil.isTrue(grade==null || !(grade==0 ||grade==1 || grade==2),"菜单层级不合法！");
        AssertUtil.isTrue(moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName())!=null,"该层级下菜单重复!");
        if(grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"请指定二级菜单url值");
            AssertUtil.isTrue(moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl())!=null,"⼆级菜单url不可重复!");
        }
        if(grade!=0){
            Integer parentId = module.getParentId();
            AssertUtil.isTrue(parentId==null || moduleMapper.selectByPrimaryKey(parentId)==null,"请指定上级菜单！");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"请输入权限码！");
        AssertUtil.isTrue(moduleMapper.queryModuleByOptValue(module.getOptValue())!=null,"权限码重复！");
        module.setIsValid(((byte)1));
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.insertSelective(module)<1,"菜单添加失败！");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module){
        AssertUtil.isTrue(module.getId()==null || moduleMapper.selectByPrimaryKey(module.getId())==null,"待更新的记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"请指定菜单名！");
        Integer grade = module.getGrade();
        AssertUtil.isTrue(grade==null || !(grade==0 ||grade==1 || grade==2),"菜单层级不合法！");
        Module temp = moduleMapper.queryModuleByGradeAndModuleName(grade, module.getModuleName());
        if(temp!=null){
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下菜单已存在！");
        }
        if(grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"请指定二级菜单url值");
            temp = moduleMapper.queryModuleByGradeAndUrl(grade, module.getUrl());
            if(temp!=null){
                AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下url已经存在！");
            }
        }

        if(grade!=0){
            Integer parentId = module.getParentId();
            AssertUtil.isTrue(parentId==null || moduleMapper.selectByPrimaryKey(parentId)==null,"请指定上级菜单！");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"请输入权限码！");
        temp =moduleMapper.queryModuleByOptValue(module.getOptValue());
        if(temp != null) {
            AssertUtil.isTrue(!(temp.getId().equals((module.getId()))), "权限码已存在!");
        }

        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module)<1,"菜单更新失败！");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModuleById(Integer mid){
        Module module = moduleMapper.selectByPrimaryKey(mid);
        AssertUtil.isTrue(mid==null || module==null,"待删除记录不存在！");
        int count = moduleMapper.countSubModuleByParentId(mid);
        AssertUtil.isTrue(count>0,"存在子菜单，不支持删除操作！");

        count =permissionMapper.countPermissionsByModuleId(mid);
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionsByModuleId(mid)<count,"菜单删除失败！");
        }
        module.setIsValid((byte)0);
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module)<1,"菜单删除失败！");

    }







}
