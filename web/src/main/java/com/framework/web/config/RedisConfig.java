package com.framework.web.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/***
 **@project: base
 **@description: redis config
 **@Author: twj
 **@Date: 2019/06/12
 **/
@Configuration
public class RedisConfig {

//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private String port;

    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    @Value("${spring.redis.password}")
    private String password;

    /***
     * 主从复制下配置
     * redis.conf 中，要开启 cluster-enabled yes(因为开启的是集群模式) 此时无法使用 slaveof 主从复制
     * @return
     */
    @Bean
    RedissonClient redissonClient(){
        Config config = new Config();
        String[] nodez = nodes.split(",");
        for(int i = 0; i < nodez.length; i++){
            nodez[i] = "redis://"+nodez[i];
        }
        //配置为主从复制模式
        config.useReplicatedServers()
                .addNodeAddress(nodez)
                .setPassword(password)
                .setScanInterval(20000)
                .setPingConnectionInterval(600000)
                .setTimeout(10000);
        return Redisson.create(config);
    }

    /**
     *  单机配置
     * @return
     */
//    @Bean
//    RedissonClient redissonClient(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://" + host + ":" + port);
//        return Redisson.create(config);
//    }

//    @Bean
//    CacheManager cacheManager(){
//        Map<String, CacheConfig> config = new HashMap<>();
//        LocalCachedMapOptions options = LocalCachedMapOptions.defaults()
//                .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.LFU)
//                .cacheSize(1000);
//        // 创建一个名称为"testMap"的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
//        config.put("testMap", new LocalCachedCacheConfig(24*60*1000, 12*60*1000, options));
//        return new RedissonSpringLocalCachedCacheManager(redissonClient, config);
//    }

}

