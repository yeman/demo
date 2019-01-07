package com.dt.demo.mongo.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface BaseMongoService<T,PK> {

    //保存一个对象到mongodb
    public T mongoSave(T bean) throws Exception;

    // 根据id删除对象
    public void mongoDeleteById(T t) throws Exception;

    // 根据对象的属性删除
    public void mongoDeleteByCondition(T t) throws Exception;

    // 通过条件查询更新数据
    public void mongoUpdate(Query query, Update update) throws Exception;

    // 根据id进行更新
    public void mongoUpdateById(PK pk, T t) throws Exception;

    //更新
    public Update mongoBuildBaseUpdate(T t) throws Exception;

    // 通过条件查询实体(集合)
    public List<T> mongoFind(Query query) throws Exception;

    public List<T> mongoFindByCondition(T t) throws Exception;

    // 通过一定的条件查询一个实体
    public T mongoFindOne(Query query) throws Exception;

    // 通过ID获取记录
    public T mongoGet(PK pk) throws Exception;

    // 通过ID获取记录,并且指定了集合名(表的意思)
    public T mongoGet(PK pk, String collectionName) throws Exception;

    public Query mongoBuildBaseQuery(T t) throws Exception;

    public MongoTemplate getMongoTemplate() throws Exception;
}
