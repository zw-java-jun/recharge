package com.house.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.entity.House;
import com.house.service.impl.HouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class FindInventory {
    @Autowired
    HouseServiceImpl houseService;
    public House findInventory(@PathVariable("money") Integer money){
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        QueryWrapper<House> money1 = wrapper.eq("money", money);
        House one = houseService.getOne(money1);
        return one;
    }
    public boolean updateInventory(@PathVariable("money") Integer money){
        House one = findInventory(money);
        int intValue = one.getInventory().intValue();
        intValue=intValue-1;
        one.setInventory(intValue);
        boolean b = houseService.updateById(one);
        return b;

    }
}
