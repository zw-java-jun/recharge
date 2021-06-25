package com.zpj.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.zpj.config.redisconfig.RedisOperator;
import com.zpj.user.entity.User;
import com.zpj.user.mapper.UserMapper;
import com.zpj.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zpj
 * @since 2021-06-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    RedisOperator redisOperator;
    @Autowired
    UserMapper userMapper;

    @Scheduled(cron = "59 0 17,5 * * ?")
    public void redisScheduled(){

        List<User> users = userMapper.selectList(null);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            list.add(String.valueOf(users.get(i).getId()));
        }

        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            Set set = redisOperator.querySet("focus:" + list.get(i));
            Set set1 = redisOperator.querySet("fans:" + list.get(i));
            String strFocus = String.join(",", set);
            String strFans = String.join(",",set1);
            User user = new User();
            user.setFocus(strFocus);
            user.setFans(strFans);
            user.setId(Long.valueOf(list.get(i)));
            userMapper.updateById(user);
        }
    }

}
