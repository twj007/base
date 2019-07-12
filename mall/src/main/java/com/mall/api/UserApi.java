package com.mall.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.util.ResultBody;
import com.mall.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import java.util.List;

/***
 **@project: base
 **@description: open api, only used for some domain, 支持跨域接口
 **@Author: twj
 **@Date: 2019/07/12
 **/
@RestController("/api/v1")
@CrossOrigin(maxAge = 3600)
public class UserApi {


    @Autowired
    private RedisTemplate redisTemplate;

    /***
     * 返回主页数据 （直接通过nginx访问静态页面，通过ajax拉取数据）
     * @return
     */
    @GetMapping("/home")
    public ResultBody home(){

        return Results.SUCCESS.result("", null);
    }



    @GetMapping("/product/{id}")
    public ResultBody getProducts(@PathVariable(value = "id", required = false)String id,
                                  @RequestParam(defaultValue = "0")int pageNum,
                                  @RequestParam(defaultValue = "5")int pageSize){

        if(StringUtils.isEmpty(id)){
            //list all with page 从redis取数据
            List<Object> products = redisTemplate.opsForList().range("products", pageNum, pageSize);
        }else{
            //看redis中有没有，没有先查数据库，再用@CachePut将数据同步缓存到redis中

        }
        return Results.SUCCESS.result("", null);
    }

    @GetMapping("/category/{category}")
    public ResultBody getProductsByCategory(@PathVariable(value = "category", required = false)String category,
                                            @RequestParam(defaultValue = "0")int pageNum,
                                            @RequestParam(defaultValue = "5")int pageSize){
        return Results.SUCCESS.result("", null);
    }

    /***
     * 搜索引擎查询
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public ResultBody search(@RequestParam(defaultValue = "0")int pageNum,
                             @RequestParam(defaultValue = "5")int pageSize,
                             @RequestParam(defaultValue = "1")int sort,
                             String keyword){
        Pageable page = PageRequest.of(pageNum, pageSize);
        // elasticsearch
        // 构建查询条件： booleanquery 品牌，类型， matchquery 品牌，类型，描述，价格 排序 按 品牌， 类型， 价格
        if(StringUtils.isEmpty(keyword)){
            //全文查询， 或者按照自定义的分数排序查询 相关度查询
        }else{
            //对关键词match查询
        }
        return Results.SUCCESS.result("", null);
    }


}
