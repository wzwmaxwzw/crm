package com.max.crm.dao;

import com.max.crm.base.BaseMapper;
import com.max.crm.model.TreeModel;
import com.max.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    public List<TreeModel> queryAllModules();

    public List<Module> queryModules();

    public Module queryModuleByGradeAndModuleName(@Param("grade") Integer grade,@Param("moduleName") String moduleName);

    public Module queryModuleByGradeAndUrl(@Param("grade") Integer grade,@Param("url") String url);

    public Module queryModuleByOptValue(String optValue);

    public Integer countSubModuleByParentId(Integer mid);

}