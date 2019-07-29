package com.fhz.microservicecommonapi.controller;

import com.alibaba.fastjson.JSON;
import com.fhz.microservicecommonapi.annotation.AnalysisActuator;
import com.fhz.microservicecommonapi.entity.WeatherForecast;
import com.fhz.microservicecommonapi.service.WeatherService;
import com.fhz.microservicecommonapi.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fenghouzhi
 * @date 2019-07-26 - 09:23
 * @description: 天气预报相关-controller
 */
@Slf4j
@RestController
@RequestMapping("/common/weather")
public class WeatherController {

    @Resource
    private WeatherService weatherService;

    @GetMapping("/city")
    @AnalysisActuator(note = "获取城市天气预报信息")
    public String resultWeatherInfoByCityName(@RequestParam("cityName") String cityName) {
        if (null == cityName || "".equals(cityName)) {
            return "城市参数为空";
        }
        String returnStrJson;
        log.info("cityName info:    {}", cityName);
        WeatherForecast weatherForecast = weatherService.getWeatherInfoByCityNameMethod(cityName);
        if (null != weatherForecast) {
            returnStrJson = JSON.toJSONString(ResponseVO.ok("success", weatherForecast));
            log.info("returnStrJson info:    {}", returnStrJson);
            return returnStrJson;
        }
        returnStrJson = JSON.toJSONString(ResponseVO.ok("查询结果为空", null));
        log.info("returnStrJson info:    {}", returnStrJson);
        return returnStrJson;
    }

}