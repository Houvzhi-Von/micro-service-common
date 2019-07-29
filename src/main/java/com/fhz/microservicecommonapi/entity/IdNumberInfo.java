package com.fhz.microservicecommonapi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fenghouzhi
 * @date 2019-07-29 - 09:51
 * @description:
 */
@Data
public class IdNumberInfo implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;

    private String area;
    private String sex;
    private String birthday;
    private String verify;

}