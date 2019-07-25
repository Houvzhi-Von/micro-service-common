package com.fhz.microservicecommonapi;

import com.fhz.microservicecommonapi.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description: 项目启动类
 * @Author: fenghouzhi
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class MicroServiceCommonApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCommonApiApplication.class, args);
        log.info("启动完成,ip地址 = {}", IpUtil.getLocalIP());
    }

}