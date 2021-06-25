package com.order.service;

import com.order.entity.Orderinfo;
import com.order.service.impl.OrderinfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OrderCreateService {

    @Autowired
    OrderinfoServiceImpl orderinfoService;
    public boolean orderCreate(@RequestBody  Orderinfo orderinfo){
        boolean save = orderinfoService.save(orderinfo);
        return save;
    }
}
