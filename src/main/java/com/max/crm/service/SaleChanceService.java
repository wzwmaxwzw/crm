package com.max.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.crm.base.BaseService;
import com.max.crm.dao.SaleChanceMapper;
import com.max.crm.enums.DevResult;
import com.max.crm.enums.StateStatus;
import com.max.crm.query.SaleChanceQuery;
import com.max.crm.utils.AssertUtil;
import com.max.crm.utils.PhoneUtil;
import com.max.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery query){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<SaleChance> pageInfo=new PageInfo<>(saleChanceMapper.selectByParams(query));
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void saveSalChance(SaleChance saleChance){
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());

        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        saleChance.setIsValid(1);
        saleChance.setUpdateDate(new Date());
        saleChance.setCreateDate(new Date());

        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)!=1,"营销机会数据添加失败！");


    }

    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"请输入客户名！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"请输入联系人！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"请输入手机号！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"手机号的格式不正确！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance){
        AssertUtil.isTrue(saleChance.getId()==null,"更新的记录不存在！");
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(temp==null,"更新的记录不存在！");
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());

        saleChance.setUpdateDate(new Date());
        if(StringUtils.isBlank(temp.getAssignMan())){
            if(!StringUtils.isBlank(saleChance.getAssignMan())){
                saleChance.setState(StateStatus.STATED.getType());
                saleChance.setDevResult(DevResult.DEVING.getStatus());
                saleChance.setAssignTime(new Date());
            }
        }else {
            if(StringUtils.isBlank(saleChance.getAssignMan())){
                saleChance.setState(StateStatus.UNSTATE.getType());
                saleChance.setDevResult(DevResult.UNDEV.getStatus());
                saleChance.setAssignTime(null);
            }else {
                if(!saleChance.getAssignMan().equals(temp.getAssignMan())){
                    saleChance.setAssignTime(new Date());
                }else {
                    saleChance.setAssignTime(temp.getAssignTime());
                }
            }
        }

        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)!=1,"营销机会数据更新失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(ids==null ||ids.length==0,"请选择需要删除的数据");
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids)<0,"营销机会数据删除失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id,Integer devResult){
        AssertUtil.isTrue(id==null,"待更新记录不存在！");
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(saleChance==null,"待更新记录不存在！");
        saleChance.setDevResult(devResult);
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)<1,"机会数据更新失败！");
    }

}
