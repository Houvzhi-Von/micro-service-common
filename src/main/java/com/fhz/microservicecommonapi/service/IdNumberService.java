package com.fhz.microservicecommonapi.service;

import com.fhz.microservicecommonapi.entity.IdNumberInfo;

/**
 * @author fenghouzhi
 * @date 2019-07-29 - 09:50
 * @description:
 */
public interface IdNumberService {

    IdNumberInfo getIdNumberInfoByIdNumber(String idNumber);

}