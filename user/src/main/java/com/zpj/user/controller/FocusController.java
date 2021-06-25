package com.zpj.user.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zpj.Result;
import com.zpj.config.redisconfig.RedisOperator;
import com.zpj.user.entity.User;
import com.zpj.user.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 关注
 * </p>
 *
 * @author zpj
 * @since 2021-06-17
 */
@RestController
@RequestMapping("/focus/")
@CacheConfig(cacheNames = "focus")
public class FocusController {
    @Autowired
    RedisOperator redisOperator;
    @Autowired
    UserMapper userMapper;


    // 添加关注人到自己关注列表
    @ApiOperation("添加关注")
    @SaCheckLogin
    @GetMapping("woman/{id}/{status}")
    public Result focus(@PathVariable("id") String id, @PathVariable("status") Integer status) {
        if (status == 0) {
            Long aLong = redisOperator.setAdd("focus:" + StpUtil.getLoginId(), id);// 我关注了谁
            redisOperator.setAdd("fans:" + id, (String) StpUtil.getLoginId());// 谁被我关注了
            System.out.println(aLong);
            return Result.succ("关注成功");
        } else {
            Long remove = redisOperator.removeSet("focus:" + StpUtil.getLoginId(), id);
            return Result.succ("删除成功", remove);
        }

    }

    // 查看我关注了哪些人
    // 只有登陆状态下才可以查看
    @ApiOperation("我关注的人")
    @SaCheckLogin
    @GetMapping("usFocus")
    public Result usFocus() {
        Set members = redisOperator.querySet("focus:" + StpUtil.getLoginId());
        System.out.println(members);
        return Result.succ("我关注的人有：", members);
    }

    // 我被谁关注了
    @ApiOperation("关注我的人")
    @SaCheckLogin
    @GetMapping("beFocused")
    public Result beFocused() {
        Set set = redisOperator.querySet("fans:" + StpUtil.getLoginId());
        System.out.println(set);
        return Result.succ("关注我的人有：" + set);
    }
    @GetMapping("allPeople")
    public List<User> allPeople(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("id",StpUtil.getLoginId());
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
        return users;
    }

}
