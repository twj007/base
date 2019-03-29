package com.base.front;

import sun.misc.Unsafe;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.stream.Stream;

/***
 **@project: base
 **@description: it's a future demo
 **@Author: twj
 **@Date: 2019/03/28
 **/
public class FutureDemo {

    private static final HashMap l = new HashMap();

    private static final ConcurrentHashMap k = new ConcurrentHashMap();

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        User user = new User();
//        user = function(user);
//        System.out.println(user);
//
//        System.out.println(user.hashCode());
        Long now = System.currentTimeMillis();
        Callable t = () -> {
            //do something
            Thread.sleep(2000);
            l.put("1", "123");
            k.putIfAbsent("2", "aaa");
            System.out.println("put 1");
            System.out.println("put 2");
            //if(true)
            //throw new RuntimeException("ex");
            return "OK";
        };
        FutureTask<String> rs = new FutureTask<>(t);
        new Thread(rs).start();
        Thread.sleep(2000);
        l.put("1", "123");
        k.putIfAbsent("2", "123");
        System.out.println("put  1");
        System.out.println("put  2");
        String words = "do something";
        String res = "";
        try {
            res = rs.get(5000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(words + res);
        Long end = System.currentTimeMillis();
        System.out.println(end - now);
        withNoFuture();
        System.out.println(k.get("2"));
    }

    public static void withNoFuture() throws InterruptedException {
        Long now = System.currentTimeMillis();
        tt t = new tt();
        t.start();
        t.join();
        String res = t.t;
        Thread.sleep(2000);

        String words = "do something";
        System.out.println(words + res);
        Long end = System.currentTimeMillis();
        System.out.println(end - now);
    }

    static class tt extends Thread{
        private String t;
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                t = "ok";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
//    public static User function(User user){
//        User u = new User();
//        u.setName("123");
//        user = u;
//        System.out.println(user.hashCode());
//        return u;
//    }
}
