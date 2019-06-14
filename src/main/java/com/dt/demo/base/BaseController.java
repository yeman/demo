package com.dt.demo.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dt.demo.util.MapUtils;
import com.dt.demo.web.ResponseResult;
import com.dt.demo.web.StatusCode;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Description: 基础控制器
 * Date: 2018-12-06 09:58
 * Author: YM
 **/
public class BaseController<M extends BaseServiceImpl,T> {
    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private M baseService;

  // //@ApiOperation(value = "saveOrUpdate",notes = "保存或更新", response = ResponseResult.class)
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public ResponseResult saveOrUpdate(@RequestBody T t) throws IllegalAccessException, InstantiationException {
        ResponseResult responseResult = new ResponseResult();
        Object id = null;
        Field idField;
        T entity;
        Class<T> clz = ReflectionKit.getSuperClassGenericType(getClass(),1);

        idField = ReflectionUtils.findField(clz,new ReflectionUtils.AnnotationFieldFilter(Id.class), false);
        entity = clz.newInstance();
        try {
            if(idField!=null){
                String fieldName = idField.getName();
                Method method= clz.getDeclaredMethod("get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1),null);
                id = method.invoke(t,null);
            }

            ConvertUtils.register(new LongConverter(null), Long.class);
            ConvertUtils.register(new ShortConverter(null), Short.class);
            ConvertUtils.register(new IntegerConverter(null), Integer.class);
            ConvertUtils.register(new DoubleConverter(null), Double.class);
            ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
            ConvertUtils.register(new SqlDateConverter(null), java.util.Date.class);
            ConvertUtils.register(new SqlTimestampConverter(null),java.sql.Timestamp.class);

            if(id!=null){//id exits update
                updateUser(t);
            }else{
                createUser(t);
            }
            BeanUtils.copyProperties(entity,t);

            this.baseService.saveOrUpdate(entity);
            responseResult.setData(t);
        } catch (NoSuchMethodException e) {
            responseResult.bulid(StatusCode.STATUS_0005.getKey(),StatusCode.STATUS_0005.getDesc());
            logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            responseResult.bulid(StatusCode.STATUS_0005.getKey(),StatusCode.STATUS_0005.getDesc());
           logger.error(e.getMessage());
        }
        return responseResult;

    }

    private void createUser(T t) {

    }

    private void updateUser(T t) {
    }

   //@ApiOperation(value = "delete/{id}",notes = "删除", response = ResponseResult.class)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ResponseResult delete(@PathVariable Serializable id) {
        ResponseResult responseResult = new ResponseResult();
        try {
            boolean result = this.baseService.removeById(id);
            responseResult.setData(responseResult);
        } catch (Exception e) {
            responseResult.bulid(StatusCode.STATUS_0005.getKey(),StatusCode.STATUS_0005.getDesc());
            logger.error(e.getMessage());
        }
        return responseResult;
    }

   ////@ApiOperation(value = "batchDelete",notes = "批量删除", response = ResponseResult.class)
    @RequestMapping(value = "/batchDelete",method = RequestMethod.POST)
    public ResponseResult batchDelete(@RequestBody List ids) {
        ResponseResult responseResult = new ResponseResult();
        try {
            if(ids!=null && ids.size()>0){
                boolean result = this.baseService.removeByIds(ids);
                responseResult.setData(responseResult);
            }else{
                responseResult.bulid(StatusCode.STATUS_0004.getKey(),StatusCode.STATUS_0004.getDesc());
            }

        } catch (Exception e) {
            responseResult.bulid(StatusCode.STATUS_0005.getKey(),StatusCode.STATUS_0005.getDesc());
            logger.error(e.getMessage());
        }
        return responseResult;
    }

    @RequestMapping(value = "/showPageList" ,method = RequestMethod.POST)
    public ResponseResult showPageList(@RequestParam Map param){
        logger.info("current:"+ MapUtils.getLong(param,"current"));
        logger.info("size:"+ MapUtils.getLong(param,"size"));
        ResponseResult simpleResult = new ResponseResult();
        Page<T> page = new Page();
        if(MapUtils.getLong(param,"current")!=null){
            page.setCurrent(MapUtils.getLong(param,"current"));
        }
        if(MapUtils.getLong(param,"size")!=null){
            page.setSize(MapUtils.getLong(param,"size"));
        }
        if(MapUtils.getString(param,"desc")!=null){
            page.setDesc(MapUtils.getString(param,"desc").split(","));
        }
        if(MapUtils.getString(param,"asc")!=null){
            page.setAsc(MapUtils.getString(param,"asc").split(","));
        }
        QueryWrapper queryWrapper = new QueryWrapper<>();
        Class<T> clz = ReflectionKit.getSuperClassGenericType(getClass(),1);
        queryWrapper.setEntity(MapUtils.map2Object(param,clz));
        page = (Page<T>) this.baseService.page(page,queryWrapper);
        simpleResult.setData(page);
        return simpleResult;
    }

   //@ApiOperation(value = "根据ID查找", notes = "根据ID查找", response = ResponseResult.class)
    @RequestMapping(value = "/get/{id}" ,method = RequestMethod.GET)
    public ResponseResult get(@PathVariable  Serializable id){
        ResponseResult simpleResult = new ResponseResult();
        try {
            Object obj = this.baseService.getById(id);
            simpleResult.setData(obj);
        } catch (Exception e) {
            simpleResult.bulid(StatusCode.STATUS_0005.getKey(),StatusCode.STATUS_0005.getDesc(),null);
            logger.error(e.getMessage());
        }
        return simpleResult;
    }

}
