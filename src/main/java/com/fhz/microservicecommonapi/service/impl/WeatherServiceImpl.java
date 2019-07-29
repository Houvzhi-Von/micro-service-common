package com.fhz.microservicecommonapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fhz.microservicecommonapi.entity.WeatherForecast;
import com.fhz.microservicecommonapi.service.WeatherService;
import com.fhz.microservicecommonapi.util.ConstantUtil;
import com.fhz.microservicecommonapi.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghouzhi
 * @date 2019-07-26 - 09:40
 * @description:
 */
@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    @Override
    public WeatherForecast getWeatherInfoByCityNameMethod(String cityName) {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("city", cityName);
        paramMap.put("key", ConstantUtil.REQUEST_WEATHER_KEY);
        try {
            String resultString = HttpRequestUtil.httpGet(ConstantUtil.WEATHER_URL, paramMap);
            log.info("resultString info:    {}", resultString);
            JSONObject jsonObject = JSONObject.parseObject(resultString);
            if (jsonObject.containsKey(ConstantUtil.REASON_KEY) && ConstantUtil.REASON_TAG.equals(jsonObject.get(ConstantUtil.REASON_KEY))) {
                WeatherForecast weatherForecast = JSONObject.parseObject(JSON.toJSONString(jsonObject.get("result")), WeatherForecast.class);
                return weatherForecast;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}