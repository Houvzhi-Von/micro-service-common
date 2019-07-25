package com.fhz.microservicecommonapi.aspect;

import com.fhz.microservicecommonapi.annotation.AnalysisActuator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author fenghouzhi
 * @date 2019-07-25 - 11:54
 * @description: 切面-Aspect
 */
@Slf4j
@Aspect
@Component
public class AnalysisActuatorAspect {

    private ThreadLocal<Long> beginTime = new ThreadLocal<>();

    @Pointcut("@annotation(analysisActuator)")
    public void serviceStatistics(AnalysisActuator analysisActuator) {
    }

    @Before("serviceStatistics(analysisActuator)")
    public void doBefore(JoinPoint joinPoint, AnalysisActuator analysisActuator) {
        // 记录请求到达时间
        beginTime.set(System.currentTimeMillis());
        log.info("note: {}", analysisActuator.note());
    }

    @After("serviceStatistics(analysisActuator)")
    public void doAfter(AnalysisActuator analysisActuator) {
        log.info("statistic time:   {}, note:   {}", System.currentTimeMillis() - beginTime.get(), analysisActuator.note());
        beginTime.remove();
    }

}