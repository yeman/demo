package com.dt.demo.good.service;

import com.dt.demo.good.dao.GoodMongoDaoImpl;
import com.dt.demo.good.entity.Good;
import com.dt.demo.good.mapper.GoodMapper;
import com.dt.demo.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description: TODO
 * Date: 2018-12-06 11:42
 * Author: YM
 **/
@Service
public class GoodServiceImpl extends BaseServiceImpl<GoodMapper, Good> {
    @Resource(name = "goodMongoDaoImpl")
    private GoodMongoDaoImpl baseMongoDao;

}

