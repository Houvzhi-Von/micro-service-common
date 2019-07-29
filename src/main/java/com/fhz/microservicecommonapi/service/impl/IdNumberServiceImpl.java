package com.fhz.microservicecommonapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fhz.microservicecommonapi.entity.IdNumberInfo;
import com.fhz.microservicecommonapi.entity.WeatherForecast;
import com.fhz.microservicecommonapi.service.IdNumberService;
import com.fhz.microservicecommonapi.util.ConstantUtil;
import com.fhz.microservicecommonapi.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghouzhi
 * @date 2019-07-29 - 09:53
 * @description:
 */
@Slf4j
@Service
public class IdNumberServiceImpl implements IdNumberService {

    @Override
    public IdNumberInfo getIdNumberInfoByIdNumber(String idNumber) {
        Map<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("cardno", idNumber);
        /*
         * dtype：可传 json、xml
         */
        paramMap.put("dtype", "json");
        paramMap.put("key", ConstantUtil.REQUEST_ID_NUMBER_KEY);
        try {
            String resultString = HttpRequestUtil.httpGet(ConstantUtil.ID_NUMBER_URL, paramMap);
            log.info("resultString info:    {}", resultString);
            JSONObject jsonObject = JSONObject.parseObject(resultString);
            if (jsonObject.containsKey(ConstantUtil.REASON_KEY) && ConstantUtil.ID_NUMBER_REASON_TAG.equals(jsonObject.get(ConstantUtil.REASON_KEY))) {
                IdNumberInfo idNumberInfo = JSONObject.parseObject(JSON.toJSONString(jsonObject.get("result")), IdNumberInfo.class);
                log.info("idNumberInfo info:    {}", idNumberInfo);
                return idNumberInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}