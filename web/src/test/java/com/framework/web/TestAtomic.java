package com.framework.web;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/09
 **/
public class TestAtomic {
    /**
     * atomicInteger 保证原子性操作
     */
    private static volatile AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException{

        for(int i = 0; i < 10; i++){
            new Thread(()->{
                try{
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                for(int j = 0; j < 10; j++){
                    count.getAndIncrement();
                }
            }).start();
        }
        Thread.sleep(5000);
        System.out.println(count);
    }
}
