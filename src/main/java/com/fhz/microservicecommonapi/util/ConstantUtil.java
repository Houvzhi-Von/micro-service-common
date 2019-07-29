package com.fhz.microservicecommonapi.util;

/**
 * @author fenghouzhi
 * @date 2019-07-26 - 09:46
 * @description: 第三方数据API-地址
 */
public class ConstantUtil {

    /**
     * reason
     */
    public static final String REASON_KEY = "reason";
    public static final String WEATHER_REASON_TAG = "查询成功!";
    public static final String ID_NUMBER_REASON_TAG = "成功的返回";


    /**
     * 天气预报
     */
    public static final String WEATHER_URL = "http://apis.juhe.cn/simpleWeather/query";
    public static final String REQUEST_WEATHER_KEY = "e2f4559018e34dac213543b751b5c1d4";

    /**
     * 身份证
     */
    public static final String ID_NUMBER_URL = "http://apis.juhe.cn/idcard/index";
    public static final String REQUEST_ID_NUMBER_KEY = "1b5ca1aa679b816b51f5d3f8913be1ff";

}