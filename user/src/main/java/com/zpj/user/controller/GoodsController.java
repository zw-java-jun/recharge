package com.zpj.user.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.zpj.Result;
import com.zpj.user.entity.Goods;
import com.zpj.user.entity.User;
import com.zpj.user.mapper.GoodsMapper;
import com.zpj.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 送礼
 * </p>
 *
 * @author zpj
 * @since 2021-06-22
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;

    // 送礼物消费豆豆
    @GetMapping("giveGift/{doug}")
    public Result giveGift(@PathVariable("doug") Integer doug) {
        // 获取当前登陆账户
        // 送礼减豆豆
        User user = userMapper.selectById((Serializable) StpUtil.getLoginId());
        Integer doug1 = user.getBount();
        int newBount = doug1 - doug;
        user.setBount(newBount);
        userMapper.updateById(user);
        return Result.succ("送出了" + doug + "豆豆", user);
    }
    // 获取所有礼物
    @GetMapping("goodsShow")
    public Result goodsShow(){
        List<Goods> goods = goodsMapper.selectList(null);
        return Result.succ(goods);
    }
}

