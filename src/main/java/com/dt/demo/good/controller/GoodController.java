package com.dt.demo.good.controller;

import com.dt.demo.base.BaseController;
import com.dt.demo.good.entity.Good;
import com.dt.demo.good.service.GoodServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: TODO
 * Date: 2018-12-05 17:50
 * Author: YM
 **/
@RestController
@RequestMapping(value = "/good")
public class GoodController extends BaseController<GoodServiceImpl, Good> {

}

