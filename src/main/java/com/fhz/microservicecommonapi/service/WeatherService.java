package com.fhz.microservicecommonapi.service;

import com.fhz.microservicecommonapi.entity.WeatherForecast;

/**
 * @author fenghouzhi
 * @date 2019-07-26 - 09:39
 * @description:
 */
public interface WeatherService {

    WeatherForecast getWeatherInfoByCityNameMethod(String cityName);

}