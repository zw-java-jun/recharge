package com.zpj.user.service.impl;

import com.zpj.config.redisconfig.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Service
public class IPService {
    @Autowired
    private RedisOperator redisOperator;

    public  String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            if(ip.contains("../")||ip.contains("..\\")){
                return "";
            }
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                ip= ip.substring(0, index);
            }
            if(ip.contains("../")||ip.contains("..\\")){
                return "";
            }
            return ip;
        } else {
            ip=request.getRemoteAddr();
            if(ip.contains("../")||ip.contains("..\\")){
                return "";
            }
            if(ip.equals("0:0:0:0:0:0:0:1")){
                ip="127.0.0.1";
            }
            return ip;
        }

    }
    public long getIpTime(String ip){
        StringBuilder stringBuilder = new StringBuilder("ip:").append(ip);
        long incr = redisOperator.incr(stringBuilder.toString(), 1);
        redisOperator.expire(stringBuilder.toString(),60*60*24);
        return incr;
    }
    public String randCode(){
        String str = "1234567890";
        String strId = new String();
        for (int i = 0; i < 4; i++) {
            char c = str.charAt(new Random().nextInt(str.length()));
            strId += c;
        }
        return strId;
    }
}
