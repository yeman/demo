package com.dt.demo.good.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Description: TODO
 * Date: 2018-12-06 18:30
 * Author: YM
 **/
@Getter
@Setter
@Document("GOOD")
public class Good{
    @Id
    private String goodId;

    private String goodName;

    private boolean isValid;

}
