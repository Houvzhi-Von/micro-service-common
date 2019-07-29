package com.fhz.microservicecommonapi.controller;

import com.alibaba.fastjson.JSON;
import com.fhz.microservicecommonapi.annotation.AnalysisActuator;
import com.fhz.microservicecommonapi.entity.IdNumberInfo;
import com.fhz.microservicecommonapi.service.IdNumberService;
import com.fhz.microservicecommonapi.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fenghouzhi
 * @date 2019-07-29 - 09:49
 * @description: 身份证相关-controller
 */
@Slf4j
@RestController
@RequestMapping("/common/idnumber")
public class IdNumberController {

    @Resource
    private IdNumberService idNumberService;

    @GetMapping("/cardno")
    @AnalysisActuator(note = "获取身份证详细信息")
    public String resultIdNumberInfoByCardNo(@RequestParam("cardNo") String cardNo) {
        if (null == cardNo || "".equals(cardNo)) {
            return "身份证参数为空";
        }
        String returnStrJson;
        log.info("cardNo info:    {}", cardNo);
        IdNumberInfo idNumberInfo = idNumberService.getIdNumberInfoByIdNumber(cardNo);
        if (null != idNumberInfo) {
            returnStrJson = JSON.toJSONString(ResponseVO.ok("success", idNumberInfo));
            log.info("returnStrJson info:    {}", returnStrJson);
            return returnStrJson;
        }
        returnStrJson = JSON.toJSONString(ResponseVO.ok("查询结果为空", null));
        log.info("returnStrJson info:    {}", returnStrJson);
        return returnStrJson;
    }

}