package com.push;

import com.push.config.PushMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class IosPushApplicationTests {

    @Test
    void test2(){
        PushMessage.getPushtoSingle();
        //PushMessage.getAppPushAll();
    }
}
