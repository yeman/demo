package com.dt.demo.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dt.demo.mongo.anotation.QueryField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: TODO
 * Date: 2018-12-05 13:10
 * Author: YM
 **/

@Getter
@Setter
@Document("ORDER")
@TableName("t_order")
public class Order implements Serializable {
    @Id
    private String orderId;

    @QueryField
    private String orderCode;

   @QueryField
    private String creator;
    @QueryField
    private Date createTime;

    @QueryField
    private String modifier;

    @QueryField
    private Date modifyTime;

}
