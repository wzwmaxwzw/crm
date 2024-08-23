package com.max.crm;

import com.max.crm.base.BaseService;
import com.max.crm.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WzwTest {

    @Resource
    private UserMapper userMapper;


    @Test
    public void test01(){

        System.out.println(userMapper);
    }
}
