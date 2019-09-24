package com.mall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Charsets;
import com.mall.pojo.UmsMember;
import com.mall.service.IUmsService;
import com.mall.util.EncryptUtils;
import com.mall.util.ResultBody;
import com.mall.util.Results;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 **@project: base
 **@description: user manage system
 **@Author: twj
 **@Date: 2019/06/18
 **/
@RestController
public class UmsController {

    private static final Logger logger = LoggerFactory.getLogger(UmsController.class);

    @Value("${spring.redis.lock.key}")
    private String key;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IUmsService umsService;

    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("/index");
    }


    @PostMapping("/login")
    public ResultBody login(UmsMember user, BindingResult result, HttpServletResponse response){
        if(result.hasErrors()){
            return Results.BAD__REQUEST.result("登陆失败", result.getAllErrors());
        }else{
            //String password = EncryptUtils.MD5Encode(EncryptUtils.AESDecode(user.getPassword()), "UTF-8");
            // check username password
            String token = EncryptUtils.encode(user.getUsername(), user.getUsername());
            response.setHeader("token", token);
            return Results.SUCCESS.result("成功", token);
        }

    }

    @GetMapping("/test")
    public ResultBody<String> test(HttpServletResponse response){

        RLock lock = redissonClient.getLock(key);
        RBucket<String> test = null;
        String value = "";
        try{
            lock.tryLock(3000, TimeUnit.MILLISECONDS);
            test = redissonClient.getBucket("test");
            test.set("ok");
            value = test.get();
        } catch (Exception e) {
            logger.error("【redis interrupted】: {}", e.fillInStackTrace());
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return Results.SUCCESS.result("ok", value);
    }

    @GetMapping("/testDatasource")
    public ResultBody<List<UmsMember>> testDataSource(HttpServletRequest request){
        Page page = PageHelper.startPage(0, 5);
        List<UmsMember> list = umsService.getAll();
        page = PageHelper.startPage(0, 5);
        List<UmsMember> list2 = umsService.getRemoteAll();
        list.addAll(list2);
        return Results.SUCCESS.result("ok", list);
    }

}
