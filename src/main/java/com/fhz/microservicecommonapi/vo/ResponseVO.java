package com.fhz.microservicecommonapi.vo;

import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author fenghouzhi
 * @date 2019-07-26 - 09:35
 * @description: 响应对象
 */
@ToString
public class ResponseVO extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = -8713837118340960775L;

    /**
     * 成功
     */
    private static final Integer SUCCESS = 200;

    /**
     * 警告
     */
    private static final Integer WARN = 201;

    /**
     * 异常 失败
     */
    private static final Integer FAIL = 500;

    public ResponseVO() {
        put("code", SUCCESS);
        put("msg", "操作成功");
    }

    public static ResponseVO error(Object msg) {
        ResponseVO responseVo = new ResponseVO();
        responseVo.put("code", FAIL);
        responseVo.put("msg", msg);
        return responseVo;
    }

    public static ResponseVO warn(Object msg) {
        ResponseVO responseVo = new ResponseVO();
        responseVo.put("code", WARN);
        responseVo.put("msg", msg);
        return responseVo;
    }

    public static ResponseVO ok(Object msg, Object data) {
        ResponseVO responseVo = new ResponseVO();
        responseVo.put("code", SUCCESS);
        responseVo.put("msg", msg);
        responseVo.put("data", data);
        return responseVo;
    }

    public static ResponseVO ok() {
        return new ResponseVO();
    }

    public static ResponseVO error() {
        return ResponseVO.error("");
    }

    @Override
    public ResponseVO put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}