package com.dt.demo.web;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: TODO
 * Date: 2018-12-11 11:50
 * Author: YM
 **/
@Getter
public enum  StatusCode {
    DEFAULT("0000","数据处理正确"),
    STATUS_0001("0001","数据访问失败"),
    STATUS_0002("0002","请求超时"),
    STATUS_0003("0003","权限异常"),
    STATUS_0004("0004","参数异常"),
    STATUS_0005("0005","程序内部异常");

    private String key;
    private String desc;

    StatusCode(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
