package com.zpj.user.userfeign;

import com.zpj.user.dto.HouseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "house")
public interface UserFeignClient {

    /**
     *
     * @param money
     * @return
     */
    @GetMapping("/houset/inv/{money}")
    HouseDTO getInv(@PathVariable("money") Integer money);
    @PostMapping("/houset/inv/{money}")
    boolean upInv(@PathVariable("money") Integer money);
}
