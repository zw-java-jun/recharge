package com.pj.controller;

import com.pj.redisconfig.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {
    @Autowired
    RedisOperator redisOperator;
    @GetMapping("/test")
    public void test(){
        redisOperator.set("zpj","123456");
    }
}
