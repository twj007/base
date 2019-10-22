package com.framework.web;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/11
 **/
public class RedissionTest {

    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        String address = "redis://127.0.0.1:6380";
        String password = "123456";
        //配置为主从复制模式
        config.useSingleServer()
                .setAddress(address)
                .setPassword(password)
                .setPingConnectionInterval(600000)
                .setTimeout(10000);
        RedissonClient client = Redisson.create(config);

        Runnable runnable = ()-> {
            RLock lock = client.getLock("test");
            lock.lock(1, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + " get lock");
            try {
                System.out.println(Thread.currentThread().getName() + " sleep");
                Thread.sleep(1000);
                System.out.println(client.getBucket("1").get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.interrupt();
    }
}
