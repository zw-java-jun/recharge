package com.zpj;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author tjw
 * @date 2021年05月24日 4:11 下午
 */
@Data
public class Result implements Serializable {
    /**
     * 200是正常，非200 表示异常
     */
    private Integer code;
    private String msg;
    private Object data;

    /**
     * 根据任意的数据 返回信息
     *
     * @param data 放入的数据
     * @return
     */
    public static Result succ(Object data) {
        return succ(200, "操作成功", data);
    }

    /**
     * 根据任意数据  并获取token查看它
     *
     * @param data  任意数据
     * @param Token Token
     * @return
     */
    public static Result succ(Object data, String Token) {
        System.out.println("保存的Token-我是在统一返回结果--->Authorization:" + Token);
        return succ(200, "操作成功", data);
    }


    /**
     * 根据消息+任意数据返回信息
     *
     * @param msg  消息
     * @param data 任意类型数据
     * @return
     */
    public static Result succ(String msg, Object data) {
        return succ(200, msg, data);
    }

    /**
     * 返回成功
     *
     * @param code 200是正常，非200 表示异常
     * @param msg  消息
     * @param data 放入的数据
     * @return
     */
    public static Result succ(Integer code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


    /**
     * 返回失败的原因
     *
     * @param msg 放入消息
     * @return
     */
    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

    /**
     * @param msg
     * @param data
     * @return
     */
    public static Result fail(String msg, Object data) {
        return fail(400, msg, data);
    }

    /**
     * 返回失败
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result fail(Integer code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
