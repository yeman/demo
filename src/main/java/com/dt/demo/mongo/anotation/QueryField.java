package com.dt.demo.mongo.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: TODO
 * Date: 2018-12-05 15:39
 * Author: YM
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryField {
    QueryType type() default QueryType.EQUALS;
    String name() default "";
}
