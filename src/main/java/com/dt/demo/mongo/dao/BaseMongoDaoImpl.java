package com.dt.demo.mongo.dao;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.dt.demo.mongo.anotation.QueryField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Description: TODO
 * Date: 2018-12-05 13:16
 * Author: YM
 **/
public  class BaseMongoDaoImpl<T,PK> implements BaseMongoDao<T,PK>{
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public T save(T bean) throws Exception {
        return mongoTemplate.save(bean);
    }

    @Override
    public void deleteById(T t)throws Exception {
        mongoTemplate.remove(t);
    }

    @Override
    public void deleteByCondition(T t)throws Exception {
        mongoTemplate.remove(buildBaseQuery(t),t.getClass());
    }


    @Override
    public void update(Query query, Update update)throws Exception {
        mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    @Override
    public void updateById(PK pk, T t) throws Exception{
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(pk));
        Update update = buildBaseUpdate(t);
        update(query, update);
    }



    @Override
    public List<T> find(Query query) throws Exception {
        return mongoTemplate.find(query,getEntityClass());
    }

    @Override
    public List<T> findByCondition(T t)throws Exception{
        Query query= buildBaseQuery(t);
        return (List<T>) mongoTemplate.find(query,t.getClass());
    }


    @Override
    public T findOne(Query query)throws Exception {
        return mongoTemplate.findOne(query,getEntityClass());
    }

    @Override
    public T get(PK pk)throws Exception {
        return mongoTemplate.findById(pk,getEntityClass());
    }

    @Override
    public T get(PK pk, String collectionName)throws Exception {
        return mongoTemplate.findById(pk,getEntityClass(),collectionName);
    }

    @Override
    public Query buildBaseQuery(T t) throws Exception{
        Query query = new Query();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
                Object value = field.get(t);
                if (value != null) {
                    QueryField queryField = field.getAnnotation(QueryField.class);
                    if (queryField != null) {
                        query.addCriteria(queryField.type().buildCriteria(queryField, field, value));
                    }
                }
        }
        return query;
    }
    @Override
    public Update buildBaseUpdate(T t) throws Exception {
        Update update = new Update();
        Field[] fields = t.getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                Object value = field.get(t);
                if(value!=null){
                    update.set(field.getName(),value);
                }
            }
        return update;

    }
    private  Class<T> getEntityClass() throws Exception {
        //Class clz =ReflectionKit.getSuperClassGenericType(getClass(),0);
        Class clz = (Class) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clz;
    }
    @Override
    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
    }
}
