package com.zpj;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


import com.zpj.config.redisconfig.RedisOperator;
import com.zpj.user.entity.Orderios;
import com.zpj.user.entity.Shopdou;
import com.zpj.user.entity.User;


import com.zpj.user.mapper.OrderiosMapper;
import com.zpj.user.mapper.ShopdouMapper;
import com.zpj.user.mapper.UserMapper;
import com.zpj.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Array;
import java.util.*;

@SpringBootTest
class UserApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderiosMapper orderiosMapper;
    @Autowired
    ShopdouMapper shopdouMapper;
    @Autowired
    RedisOperator redisOperator;

    @Test
    void test(){



//        Set set = redisOperator.querySet("focus:" + StpUtil.getLoginId());
//        redisOperator.querySet("fans:" + StpUtil.getLoginId());
    }
    @Test
    void test2(){
        QueryWrapper<Shopdou> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("money","6");
        Shopdou shopdou = shopdouMapper.selectOne(wrapper1);
        System.out.println(shopdou);
    }
}
