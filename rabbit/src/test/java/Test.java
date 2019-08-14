import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.convert.Bucket;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/12
 **/
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public int searchInsert(int[] nums, int target) {

        if(nums[nums.length-1] < target){
            return nums.length;
        }
        if(nums[0] > target){
            return 0;
        }
        for(int i = 0; i< nums.length; i++){
            if(nums[i] >= target){
                return i;
            }
        }
        return nums.length;
    }



    public static void main(String[] args) throws InterruptedException{

        DelayQueue<Message> queue = new DelayQueue<>();
        Message m1 = new Message(1, "test1", 3000);
        Message m2 = new Message(2, "test2", 5000);
        queue.offer(m1);
        queue.offer(m2);
        Consumer consumer = new Consumer(queue);
        Thread t1 = new Thread(consumer);
        t1.start();
//        SingletonEnum.INSTANCE.dosomething();
//        System.out.println(2 << 3);
//        Foo f = new Foo();
//        f.first(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("first");
//            }
//        });
//        f.second(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("second");
//            }
//        });
//        f.third(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("third");
//            }
//        });

//          FooBar fooBar = new FooBar(2);
//          fooBar.foo(new Thread(()-> {
//              System.out.print("foo");
//          }));
//          fooBar.bar(new Thread(()-> {
//              System.out.print("bar");
//          }));
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//
//        RedissonClient redisson = Redisson.create(config);
//        final RReadWriteLock lock = redisson.getReadWriteLock("lock");
//
//        lock.writeLock().tryLock();
//
//        Thread t = new Thread() {
//            public void run() {
//                RLock r = lock.readLock();
//                r.lock();
//
//                try {
//                    RBucket<Integer> val = redisson.getBucket("iphone");
//                    System.out.println(val.get());
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                r.unlock();
//            }
//        };
//
//        t.start();
//        t.join();
//
//        lock.writeLock().unlock();
//
//        t.join();
//
//        redisson.shutdown();
//        try {
//
//            ServerSocket ss = new ServerSocket(8083, 0, InetAddress.getByName("127.0.0.1"));
//            while(true){
//                Socket client = ss.accept();
//                System.out.println(client.isConnected());
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }
}

/****
 * 懒汉式
 */
class Singleton{

    private static Singleton singleton;

    private Singleton(){}

    public static Singleton getInstance(){
        if(singleton == null){
            singleton =  new Singleton();
        }
        return singleton;
    }


}

/***
 * 饥汉式
 */
class SingletonHungry{
    private static SingletonHungry singleton = new SingletonHungry();

    private SingletonHungry(){

    }

    public static SingletonHungry getInstance(){
        return singleton;
    }
}

/***
 * 双重检查锁
 */
class SingletonDouble{

    private static SingletonDouble singletonDouble;

    private SingletonDouble(){

    }

    public static SingletonDouble getInstance(){

        if(singletonDouble == null){
            synchronized (singletonDouble.getClass()) {
                singletonDouble = new SingletonDouble();
            }
        }
        return singletonDouble;
    }
}

/**
 * 枚举，自带单例特性
 */
enum SingletonEnum{
    INSTANCE;

    public void dosomething(){
        System.out.println("ok");
    }
}


/***
 * 插头
 */
interface Target{
    void domethod();
}

abstract class Adaptee {
    abstract void doAdpatee();
}

class Adapter extends Adaptee implements Target{

    @Override
    public void domethod() {

    }

    @Override
    void doAdpatee() {

    }
}

class Foo {

    private CountDownLatch b;
    private CountDownLatch c;

    public Foo() {

        b = new CountDownLatch(1);
        c = new CountDownLatch(1);
    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        b.countDown();

    }

    public void second(Runnable printSecond) throws InterruptedException {
        b.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        c.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        c.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
class FooBar {
    private int n;
    public static Semaphore seam_foo = new Semaphore(0);
    public static Semaphore seam_bar = new Semaphore(1);
    private volatile boolean flag = false;
    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {

//            seam_bar.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            while(!flag) {
                Thread.yield();
            }
            printFoo.run();
            flag = true;

//            seam_bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // printBar.run() outputs "bar". Do not change or remove this line.
//            seam_foo.acquire();
            while(flag) {
                Thread.yield();
            }
            printBar.run();
            flag = false;
//            seam_foo.release();
        }
    }
}

//class FooBar {
//    private int n;
//    private Semaphore semaphore = new Semaphore(0);
//    private Semaphore semaphore2 = new Semaphore(1);
//    public FooBar(int n) {
//        this.n = n;
//    }
//
//    public void foo(Runnable printFoo) throws InterruptedException {
//
//        for (int i = 0; i < n; i++) {
//            semaphore2.acquire();
//            // printFoo.run() outputs "foo". Do not change or remove this line.
//            printFoo.run();
//            semaphore.release();
//        }
//    }
//
//    public void bar(Runnable printBar) throws InterruptedException {
//        for (int i = 0; i < n; i++) {
//            semaphore.acquire();
//            // printBar.run() outputs "bar". Do not change or remove this line.
//            printBar.run();
//            semaphore2.release();
//        }
//    }
//}