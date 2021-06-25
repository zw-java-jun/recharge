package com.zpj.user.controller;


import com.zpj.user.entity.Shopdou;
import com.zpj.user.mapper.ShopdouMapper;
import com.zpj.user.service.impl.ShopdouServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 可销售商品
 * </p>
 *
 * @author zpj
 * @since 2021-06-19
 */
@RestController
@RequestMapping("/shopdou/")
public class ShopdouController {
    @Autowired
    // 返回所有商品信息
    ShopdouMapper shopdouMapper;

    @ApiOperation("返回能售卖的商品")
    @GetMapping("shopShow")
    public List<Shopdou> shopShow() {
        List<Shopdou> shopdous = shopdouMapper.selectList(null);
        return shopdous;
    }

}

