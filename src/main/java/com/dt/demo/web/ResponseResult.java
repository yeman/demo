package com.dt.demo.web;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: TODO
 * Date: 2018-12-11 11:48
 * Author: YM
 **/
@Getter
@Setter
public class ResponseResult {
    private String statuCode = StatusCode.DEFAULT.getKey();
    private String status = StatusCode.DEFAULT.getDesc();
    private Object data;

    public ResponseResult() {

    }
    public ResponseResult(String statuCode, String status, Object data) {
        this.statuCode = statuCode;
        this.status = status;
        this.data = data;
    }
    public ResponseResult(String statuCode, String status) {
        this.statuCode = statuCode;
        this.status = status;
    }

    public ResponseResult bulid(String statuCode, String status,Object data) {
        this.status = status;
        this.statuCode = statuCode;
        this.data = data;
        return this;
    }
    public ResponseResult bulid(String statuCode, String status) {
        return bulid(statuCode,status,null);
    }
}
