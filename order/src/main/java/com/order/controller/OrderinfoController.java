package com.order.controller;


import com.order.entity.Orderinfo;
import com.order.mapper.OrderinfoMapper;
import com.order.service.OrderCreateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zpj
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/ordert")
@Slf4j
public class OrderinfoController {

    @Autowired
    OrderCreateService orderCreateService;
    @Autowired
    OrderinfoMapper orderinfoMapper;
    @PutMapping("/create")
    public boolean createOrder(@RequestBody Orderinfo orderinfo){
        log.info("我被调用了");
        boolean orderCreate = orderCreateService.orderCreate(orderinfo);
        if(orderCreate){
            log.info("创建订单成功");
        }else {
            log.info("创建订单失败");
        }
        return orderCreate;
    }
    @GetMapping("/getmes/{id}")
    public Orderinfo getmes(@PathVariable("id") Long id){
        Orderinfo orderinfo = orderinfoMapper.selectById(id);
        return orderinfo;
    }


}

