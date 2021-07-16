package org.rhett.common.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author Rhett
 * @Date 2021/6/18
 * @Description
 * JWT自定义载荷信息对象
 */
@Data
public class Payload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
