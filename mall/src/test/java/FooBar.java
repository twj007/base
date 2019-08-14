import org.springframework.beans.factory.BeanFactory;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;

import java.net.URI;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/22
 **/
public class FooBar {
    private int n;

    public static void main(String[] args) throws Exception{
//        FooBar fooBar = new FooBar(2);

//        String a = "aa";
//        String b = a.intern();
//        System.out.println(a == b);
//
//        System.out.println(a.equals(b));
        Map m = new HashMap();
        m.put(null, null);
        System.out.println(m);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            String nex = (String) iterator.next();
            if("2".equals(nex)){
                iterator.remove();
            }
        }
//        for(String s : list){
//            if("2".equals(s)){
//                list.remove(s);//抛出异常
//            }
//        }
        System.out.println(list);
//        ReentrantLock lock = new ReentrantLock();
//
//        Runnable r1 = new Runnable() {
//            @Override
//            public void run() {
//                System.out.print("foo");
//            }
//        };
//        Runnable r2 = new Runnable() {
//            @Override
//            public void run() {
//                System.out.print("bar");
//            }
//        };
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        final Semaphore semaphore = new Semaphore(1);
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        for (int i = 0; i < 1 ; i++) {
//            executorService.execute(() -> {
//                try {
//                    semaphore.acquire();
//                    fooBar.foo(r1);
//                    semaphore.release();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                countDownLatch.countDown();
//            });
//        }
//        for (int i = 0; i < 1 ; i++) {
//            executorService.execute(() -> {
//                try {
//                    semaphore.acquire();
//                    fooBar.bar(r2);
//                    semaphore.release();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                countDownLatch.countDown();
//            });
//        }
//        countDownLatch.await();
//        executorService.shutdown();


    }

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        Lock lock  = new ReentrantLock();
        try {

            lock.lock();
            for (int i = 0; i < n; i++) {

                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
        }
    }
}