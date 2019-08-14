import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/05
 **/
public class TestThread {



    public void test(final int i){
        ConcurrentHashMap map = new ConcurrentHashMap();
        Collections.synchronizedSet(new HashSet<>());

    }

    public static void main(String[] args) {

        Foo f = new Foo();
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                f.add();
            }
        });
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                f.minus();
            }
        });
        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                f.add();
            }
        });
        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                f.minus();
            }
        });
        a.start();
        b.start();
        c.start();
        d.start();
    }
}

class Foo {

    int i = 0;

    Lock lock = new ReentrantLock();

    public void add(){
        try{
            lock.lock();
            i ++;
            System.out.println(Thread.currentThread() + ":"+i);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void minus(){
        try{
            lock.lock();
            i --;
            System.out.println(Thread.currentThread() + ":" +i);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }




}
