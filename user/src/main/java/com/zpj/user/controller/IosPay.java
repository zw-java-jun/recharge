package com.zpj.user.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zpj.Result;
import com.zpj.config.iospay.IosVerifyUtil;
import com.zpj.user.entity.Orderios;
import com.zpj.user.entity.Shopdou;
import com.zpj.user.entity.User;
import com.zpj.user.mapper.OrderiosMapper;
import com.zpj.user.mapper.ShopdouMapper;
import com.zpj.user.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ls.LSOutput;

import javax.lang.model.element.VariableElement;
import java.util.*;

@SaCheckLogin
@RestController
public class IosPay {
    @Autowired
    OrderiosMapper orderiosMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ShopdouMapper shopdouMapper;

    @ApiOperation("返回订单")
    @PostMapping("/orderIos")
    public Map<String, String> orderIos(@RequestBody Shopdou shopdou){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("dou:",shopdou.getDou().toString());
        hashMap.put("createTime:", new Date().toString());
        hashMap.put("createId:", (String) StpUtil.getLoginId());
        hashMap.put("money:",shopdou.getMoney().toString());
        hashMap.put("status:","0");
        hashMap.put("orderuuid:",uuid);
        return hashMap;
    }

    @PostMapping("/chongzhi")
    @ApiOperation("苹果内购支付返回")
    public Result iosConfirmOrder(@RequestBody Map<String, String> map) {
        //System.out.println("这是自动订阅的id："+map.get("originTransaction_id"));
        String orderuuid = map.get("orderuuid");
        String createId = map.get("createId");
        String money = map.get("money");
        String password = map.get("password");
        System.out.println("秘钥为："+password);
        System.out.println("订单标识："+orderuuid+"  钱："+money+"  创建者id："+createId);
        String transaction_id1 = map.get("transaction_id");
        System.out.println("订单id："+transaction_id1);
        //获取凭证
        String receipt = map.get("receipt");

        //首先调用正式环境查看验证结果;
        String verifyResult = IosVerifyUtil.buyAppVerify(receipt,password, 1);//type:1->正式环境,发送平台验证
        if (verifyResult == null) {
            //如果验证结果不存在;提示前端;订单信息不存在;
            return Result.succ("充值不成功");
        }
        JSONObject job = JSONObject.parseObject(verifyResult);

        String states = job.getString("status");

        //states为21007表示,收据信息是沙盒测试用;需要请求沙盒测试地址对凭证进行校验
        if ("21007".equals(states)) {
            //如果正式环境校验不成功,则请求沙盒测试地址对凭证进行校验
            verifyResult = IosVerifyUtil.buyAppVerify(receipt,password, 0);//type:0->沙盒测试,发送平台验证
            job = JSONObject.parseObject(verifyResult);
            states = job.getString("status");
        }
        if (states.equals("0")) {
            // 前端所提供的收据是有效的,验证成功

            String r_receipt = job.getString("receipt");
            System.out.println("这是receipt："+r_receipt);
            JSONObject returnJson = JSONObject.parseObject(r_receipt);
            String in_app = returnJson.getString("in_app");
            JSONArray jsonArray = JSONArray.parseArray(in_app);
            List<String> listTransaction = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String transaction_id = jsonObject.getString("transaction_id");
                listTransaction.add(transaction_id);
            }
            //业务代码
            if(listTransaction.contains(transaction_id1)){
                QueryWrapper<Orderios> wrapper = new QueryWrapper<>();
                wrapper.eq("orderuuid",orderuuid);
                Orderios orderios = orderiosMapper.selectOne(wrapper);
                if(orderios==null){
                    // 充值豆豆操作
                    Long id = Long.valueOf(createId);
                    int money2=Integer.valueOf(money);
                    int dou= money2*7;
                    User user1 = userMapper.selectById(id);
                    Integer bount1 = user1.getBount();
                    int bount;
                    User user = new User();
                    user.setId(id);
                    bount=dou+bount1;
                    user.setBount(bount);
                    System.out.println("购买后的用户豆豆"+bount);
                    userMapper.updateById(user);
                    // 将订单写入数据库
                    Orderios order = new Orderios();
                    order.setOrderuuid(orderuuid);
                    order.setStatus(1);
                    order.setMoney(Integer.valueOf(money));
                    order.setTransactionId(transaction_id1);
                    order.setCreateId(1L);
                    order.setDou(dou);
                    orderiosMapper.insert(order);
                }else {
                    return Result.fail("该订单已经充值过，刷单！！！");
                }
            }else {
                return Result.fail("订单id不一致");
            }
            //支付成功
            return Result.succ("支付成功");
        } else {
            //支付失败
            return Result.fail("支付失败"+states);
        }

    }
}
//    //获取产品编号
//    String product_id = jsonArray.getJSONObject(i).get("product_id").toString();
//                        System.out.println(product_id);
//                                //获取订单价格
//                                String[] split = product_id.split("com.zxdemo.shoutao.");
//                                //处理自己的逻辑，将transaction_id存入数据库，完成订单
//                                String price = split[1];
//                                System.out.println("我是获取的价格：" + price);