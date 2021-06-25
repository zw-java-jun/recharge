package com.zpj.user.controller;


import com.zpj.user.entity.Orderios;
import com.zpj.user.entity.Shopdou;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 *  前端控制
 * </p>
 *
 * @author zpj
 * @since 2021-06-19
 */
@RestController
@RequestMapping("/orderios/")
public class OrderiosController {

    // 返回订单信息接口
    @GetMapping("orderIos")
    public Orderios orderIos(@RequestBody Shopdou shopdou){
        Orderios orderios = new Orderios();
        orderios.setDou(shopdou.getDou());
        orderios.setCreateTime(new Date());
        orderios.setMoney(shopdou.getMoney());
        orderios.setStatus(0);
        return orderios;
    }

}

