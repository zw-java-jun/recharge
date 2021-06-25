package com.push.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class PushMessage{

    // 1：获取应用基本信息
//    private static String appId = "C5w0mu5ZZq5NgvJO2pFQi5";
//    private static String appKey = "mxTTWGUmB56I2a7iimtNb4";
//    private static String masterSecret = "u3SODac9ti7oiIfg8h0774";
//    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
//    private static String cid = "cbc159d50255e7b6afb65e51778fa363";
    private static String appId = "RDCXqlIjLt7MaX0V06BI62";
    private static String appKey = "kWfz0OA2hj9o3Gxf13pRi3";
    private static String masterSecret = "lkIqNbHUGcAO176YYriy18";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    private static String cid = "403f002c4cb7f6fba47d7c6a3ddd9777";
    /**
     *
     * @Function: getAppPushAll
     * @Description: 单推
     * @version: v1.0.0
     * @author: qiuyujiao
     * @date: 2019年11月7日
     */
    public static void getPushtoSingle(){
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        TransmissionTemplate template = getTemplate();
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
        System.out.println(ret.getResponse().toString());
    }

    /**
     *
     * @Function: getTemplate
     * @Description: 通知模板
     * @version: v1.0.0
     * @author: qiuyujiao
     * @date: 2019年11月7日
     */
    public static TransmissionTemplate getTemplate() {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setTransmissionContent("aabb");//透传的内容，开发者自行处理
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent("xxbb");
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        payload.setSound("xiaoshizi.mp3");
        payload.setContentAvailable(2);
        // 字典模式使用APNPayload.DictionaryAlertMsg
        payload.setAlertMsg(getDictionaryAlertMsg());
        template.setAPNInfo(payload);
        return template;
    }

    /**
     *
     * @Function: getDictionaryAlertMsg
     * @Description: 【通知样式】iOS通知样式设置
     * ios一定要用改方法调试，平板上才会显示推送的消息
     * @version: v1.0.0
     * @author: qiuyujiao
     * @date: 2019年11月7日
     */
    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg() {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody("有一位女子查看了你");
        alertMsg.setActionLocKey("000");
        alertMsg.setLocKey("111");
        alertMsg.addLocArg("2222");
        alertMsg.setLaunchImage("333.png");
        // iOS8.2以上版本支持
        alertMsg.setTitle("婚恋交友");
        alertMsg.setTitleLocKey("8.2头");
        alertMsg.addTitleLocArg("8.2部");
        return alertMsg;
    }

    /**
     *
     * @Function: getAppPushAll
     * @Description: 群推
     * @version: v1.0.0
     * @author: qiuyujiao
     * @date: 2019年11月7日
     */
    public static void getAppPushAll(){
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        TransmissionTemplate template = getTemplate();
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setOffline(true);
        // 离线有效时间，单位为毫秒
        message.setOfflineExpireTime(24 * 1000 * 3600);
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);
        message.setOffline(true);
        // 离线有效时间，单位为毫秒
        message.setOfflineExpireTime(24 * 1000 * 3600);
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }


}