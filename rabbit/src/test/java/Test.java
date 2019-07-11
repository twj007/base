import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.convert.Bucket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/12
 **/
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws InterruptedException{
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//
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
        try {

            ServerSocket ss = new ServerSocket(8083, 0, InetAddress.getByName("127.0.0.1"));
            while(true){
                Socket client = ss.accept();
                System.out.println(client.isConnected());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
