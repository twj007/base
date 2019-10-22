package com.framework.web.controller

import com.framework.web.pojo.JmxMessage;
import org.codehaus.groovy.control.CompilerConfiguration
import org.redisson.api.RBucket
import org.redisson.api.RLock
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct
import javax.script.ScriptEngine;
import java.lang.management.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.TemporalField
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/09
 **/
@RestController
@RequestMapping("/groovy")
class GroovyScriptController {

    private GroovyShell groovyShell

    @Autowired
    private Binding groovyBinding

    @PostConstruct
    void init(){
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader())
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.setSourceEncoding("utf-8")
        //执行的脚本
        //compilerConfiguration.setScriptBaseClass(TestScript.class.getName())

        groovyShell = new GroovyShell(groovyClassLoader, groovyBinding, compilerConfiguration)
    }

    @RequestMapping("/test")
    def test(){
        return "not ok"
    }

    @RequestMapping(value = "/execute", method = RequestMethod.GET)
    //前端动态传递groovy脚本，通过权限控制用户对标本的使用  @RequireRole()
    def execute(String scriptContent) {
        Script script = groovyShell.parse(scriptContent)
        return String.valueOf(script.run())
    }

    @RequestMapping("/executeSql")
    def executeSql(String id){
//        执行的权限必须控制好，当脚本被保存在数据库中时，通过动态生成脚本文件，执行并返回结果。
//        String scriptContent = webService.getScriptById(id);
//        Script script = groovyShell.parse(scriptContent);
//        return String.valueOf(script.run());
        return ""
    }

    @RequestMapping("/invoke")
    def invoke(String filename) throws IOException {
        File file = new File(ResourceUtils.CLASSPATH_URL_PREFIX + "/com/framework/web/script/"+filename+".class")
        List list = new ArrayList()
        if(!file.exists())
            return "文件不存在"
        Object result = groovyShell.run(file, list)
        return String.valueOf(result)
    }

    @RequestMapping("/jmx")
    def jmxInfo(){

        def os = ManagementFactory.operatingSystemMXBean
        println """OPERATING SYSTEM: 
                    OS architecture = $os.arch 
                    OS name = $os.name 
                    OS version = $os.version 
                    OS processors = $os.availableProcessors 
                """

        def rt = ManagementFactory.runtimeMXBean
        println """RUNTIME: 
                Runtime name = $rt.name 
                Runtime spec name = $rt.specName 
                Runtime vendor = $rt.specVendor 
                Runtime spec version = $rt.specVersion 
                Runtime management spec version = $rt.managementSpecVersion 
               """

        def mem = ManagementFactory.memoryMXBean
        def heapUsage = mem.heapMemoryUsage
        def nonHeapUsage = mem.nonHeapMemoryUsage

        println """MEMORY: 
                   HEAP STORAGE: 
                        Memory committed = $heapUsage.committed 
                        Memory init = $heapUsage.init 
                        Memory max = $heapUsage.max 
                        Memory used = $heapUsage.used NON-HEAP STORAGE: 
                        Non-heap memory committed = $nonHeapUsage.committed 
                        Non-heap memory init = $nonHeapUsage.init 
                        Non-heap memory max = $nonHeapUsage.max 
                        Non-heap memory used = $nonHeapUsage.used 
                   """

        println "GARBAGE COLLECTION:"
        def garbageCollection = ManagementFactory.garbageCollectorMXBeans.each { gc ->
            println "	name = $gc.name"
            println "		collection count = $gc.collectionCount"
            println "		collection time = $gc.collectionTime"
            String[] mpoolNames =   gc.memoryPoolNames

            mpoolNames.each {
                mpoolName -> println "		mpool name = $mpoolName"
            }
        }
        JmxMessage message = new JmxMessage()
        message.setSystemMXBean(os)
        message.setMemoryMXBean(mem)
        message.setRuntimeMXBean(rt)
        message.setGarbageCollectorMXBeans(garbageCollection)
        return message
    }

    @Autowired
    RedissonClient redissonClient

    @RequestMapping("/getValue")
    ResponseEntity getValue(String key){
        
        RBucket val = redissonClient.getBucket(key)
        return ResponseEntity.ok(val.get())

    }
    /***
     * redis 加锁
     * @param value
     * @return
     */
    @RequestMapping("/setValue")
    @ResponseBody
    String setValue(String key) throws Exception{
        if(key != null){
            RLock lock = redissonClient.getLock("redis_lock");
            try {
                lock.lock(5, TimeUnit.SECONDS)
                RBucket<Integer> bucket = redissonClient.getBucket(String.valueOf(key));
                try{
                    int num = bucket.get();
                    if (num > 0) {
                        //扣减库存
                        bucket.set(num - 1);
                        System.out.println("【redisson】 秒杀成功， 等待生成订单");
                        System.out.println("【数量】: "+ String.valueOf(num-1));
                        return "秒杀成功";

//                        }
                    } else {
                        System.out.println("【redisson】 库存不足");
                        return "库存不足";
                    }
                }catch (Exception e){
                    System.out.println("【exception】 msg:{}"+e.getStackTrace());
                    return "系统繁忙，请刷新重试";
                }finally {
                    lock.unlock();
                }
            }catch(Exception e) {
                System.out.println("【秒杀】线程中断:{}"+ e.getStackTrace());
                return "系统繁忙，请刷新重试";

            }
        }else{
            return "请输入参数";
        }
    }
}
