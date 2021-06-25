package com.zpj.user.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zpj.Result;
import com.zpj.config.redisconfig.RedisOperator;
import com.zpj.user.dto.HouseDTO;
import com.zpj.user.dto.OrderinfoDTO;
import com.zpj.user.entity.User;
import com.zpj.user.service.impl.IPService;
import com.zpj.user.service.impl.UserServiceImpl;
import com.zpj.user.userfeign.OrderInfoFeignClient;
import com.zpj.user.userfeign.UserFeignClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zpj
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    OrderInfoFeignClient orderFeignClient;
    @Autowired
    RedisOperator redisOperator;
    @Autowired
    IPService ipService;


    // 充值话费
    @SaCheckLogin
    @GetMapping("/create")
    @ApiOperation("充值话费")
    public String create(String phone, Integer money) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> number = wrapper.eq("number", phone);
        User user = userService.getOne(number);
        if (user != null) {
            HouseDTO inv = userFeignClient.getInv(money);
            if (inv != null) {
                OrderinfoDTO orderInfo = new OrderinfoDTO();
                orderInfo.setMoney(money);
                orderInfo.setNumber(phone);
                System.out.println(orderInfo);
                boolean order = orderFeignClient.createOrder(orderInfo);
                if (order) {
                    boolean b = userFeignClient.upInv(money);
                    if (b) {
                        return "库存-1";
                    } else {
                        return "减库存失败";
                    }
                } else {
                    return "创建订单失败";
                }
            } else {
                return "没有库存了";
            }
        } else {
            return "手机号输错了";
        }
    }

    // 登陆
    @PostMapping("/login")
    @ApiOperation("登陆")
    public Result login(@RequestBody Map<String, String> map) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("number", map.get("number"))
                .eq("pwd", map.get("pwd"));
        User one = userService.getOne(wrapper);
        if (one != null) {
            StpUtil.setLoginId(one.getId());
            return Result.succ("登陆成功", one);
        } else {
            return Result.fail("账号密码错误");
        }
    }

    // 注册
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(String phone, String pwd, String code) {
        // 生成key
        StringBuilder redisKey = new StringBuilder("phone:").append(phone);
        String phoneCode = redisOperator.get(redisKey.toString());
        if (code.equals(phoneCode)) {
            User user = new User();
            user.setNumber(phone);
            user.setPwd(pwd);
            userService.save(user);
            return Result.succ("验证通过");
        } else {
            return Result.fail("验证码失效");
        }
    }

    // 获取验证码
    @PostMapping("/getCode")
    @ApiOperation("获取注册验证码")
    public Result getCode(String phone, HttpServletRequest request) {
        String ipAddr = ipService.getIpAddr(request);
        // 生成key
        StringBuilder redisKey = new StringBuilder("phone:").append(phone);
        String s = redisOperator.get(redisKey.toString());
        if (s != null) {
            return Result.fail("60秒内勿多次获取");
        } else {
            // 生成验证码
            String strId = ipService.randCode();
            long ipTime = ipService.getIpTime(ipAddr);
            if (ipTime <= 10) {
                redisOperator.set(redisKey.toString(), strId, 60);
            } else if (ipTime >= 100) {
                return Result.fail("已经达到100次");
            } else {
                //service.get();
                return Result.succ("已经达到10次，启动滑动验证");
            }
            return Result.succ("验证码发送成功" + strId + "次数" + ipTime);
        }
    }
    // 验证滑动块
//    @RequestMapping(value = "/verify", method = RequestMethod.POST)
//    public Result verify(@RequestBody Map<String, String> map) {
//        System.out.println("我是滑动后验证的方法：" + map);
//        return Result.succ(service.verify(map.get("id"), Double.valueOf(map.get("x"))));
//    }


    // 登出
    @SaCheckLogin
    @ApiOperation("登出")
    @GetMapping("logout")
    public void logout() {
        StpUtil.logout();
    }

    @ApiOperation("json转对象存redis")
    @PostMapping("toJson")
    // 接受对象转成json存入redis
    public String insert(User user) {
        String s = JSON.toJSONString(user);
        redisOperator.set("user", s);
        return s;
        //redisOperator.set("user",user);
    }

    @ApiOperation("redis取json转对象")
    // 从redis取出json转对象
    @PostMapping("toObject")
    public User getRedis() {
        User user = JSON.parseObject(redisOperator.get("user"), User.class);
        return user;
    }
}
