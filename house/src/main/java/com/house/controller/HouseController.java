package com.house.controller;


import com.house.entity.House;
import com.house.service.FindInventory;
import com.house.service.impl.HouseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zpj
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/houset")
@Slf4j
public class HouseController {
    @Autowired
    HouseServiceImpl houseService;
    @Autowired
    FindInventory findInventory;

    @GetMapping("/inv/{money}")
    public House getInv(@PathVariable("money") Integer money){
        log.info("我是查库存，我被调用了");
        House inventory = findInventory.findInventory(money);
        return inventory;
    }
    @PostMapping("/inv/{money}")
    public boolean upInv(@PathVariable("money") Integer money){
        boolean b = findInventory.updateInventory(money);
        if(b){
            log.info("减库存成功");
        }
        return b;
    }

}

