package com.dt.demo.order.controller;

import com.dt.demo.good.dao.GoodMongoDaoImpl;
import com.dt.demo.good.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: TODO
 * Date: 2018-12-05 17:50
 * Author: YM
 **/
@RestController
@RequestMapping(value = "/order")
public class OrderController{
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private GoodMongoDaoImpl goodMongoDao;

    @RequestMapping(value = "/testRedis",method = RequestMethod.GET)
    public void test01(){
        redisTemplate.opsForValue().set("test01","测试");//添加
        long inc =  redisTemplate.opsForValue().increment("sheet.hb");
        String obj = (String) redisTemplate.opsForValue().get("test01");
        boolean mapflag1 = redisTemplate.opsForHash().putIfAbsent("map1","key1","value1");
        boolean mapflag2 = redisTemplate.opsForHash().putIfAbsent("map1","key1","value1");

    }
    @RequestMapping(value = "/testmongod",method = RequestMethod.POST,produces ={MediaType.APPLICATION_JSON_VALUE})
    public Object testmongo(@RequestBody Good good) throws Exception {
        Good mongo = goodMongoDao.save(good);
        return mongo;
    }
    @RequestMapping(value = "/getmongo",method = RequestMethod.GET)
    public Object getMongo(@RequestParam String id) throws Exception {
        Good mongo = goodMongoDao.get(id);
        return mongo;
    }
    @RequestMapping(value = "/queryMongo",method = RequestMethod.POST)
    public List<Good> queryMongo(@RequestBody Good good) throws Exception {
        List<Good> goodlist = goodMongoDao.findByCondition(good);
        Good queryGood = goodMongoDao.findOne(goodMongoDao.buildBaseQuery(good));
        return goodlist;
    }
    @RequestMapping(value = "/updateMongo",method = RequestMethod.POST)
    public void updateMongo(@RequestBody Good good) throws Exception {
        Update update = new Update();

        update.set("goodName","更新值");
        goodMongoDao.update(goodMongoDao.buildBaseQuery(good),update);
    }
      @RequestMapping(value = "/deletemongo",method = RequestMethod.POST)
        public void deleteMongo(@RequestBody Good good) throws Exception {
            goodMongoDao.deleteByCondition(good);
        }



}

