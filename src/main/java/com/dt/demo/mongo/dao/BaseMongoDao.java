package com.dt.demo.mongo.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Description: TODO
 * Date: 2018-12-05 14:16
 * Author: YM
 **/
public interface BaseMongoDao<T,PK> {

    //保存一个对象到mongodb
    public T save(T bean) throws Exception;

    // 根据id删除对象
    public void deleteById(T t) throws Exception;

    // 根据对象的属性删除
    public void deleteByCondition(T t) throws Exception;

    // 通过条件查询更新数据
    public void update(Query query, Update update) throws Exception;

    // 根据id进行更新
    public void updateById(PK pk, T t) throws Exception;

    //更新
    public Update buildBaseUpdate(T t) throws Exception;

    // 通过条件查询实体(集合)
    public List<T> find(Query query) throws Exception;

    public List<T> findByCondition(T t) throws Exception;

    // 通过一定的条件查询一个实体
    public T findOne(Query query) throws Exception;

    // 通过ID获取记录
    public T get(PK pk) throws Exception;

    // 通过ID获取记录,并且指定了集合名(表的意思)
    public T get(PK pk, String collectionName) throws Exception;

    public Query buildBaseQuery(T t) throws Exception;

    public MongoTemplate getMongoTemplate();
}
