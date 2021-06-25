package com.zpj.user.userfeign;

import com.zpj.user.dto.OrderinfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order")
public interface OrderInfoFeignClient {

    @PutMapping("/ordert/create")
    boolean createOrder(@RequestBody OrderinfoDTO orderInfo);
}
