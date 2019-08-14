package com.framework.web;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/29
 **/
public class TestLock {

    public static void main(String[] args) {
//       Thread t = new Thread(new DoAction());
//       Thread t2 = new Thread(new DoAction());
//       Thread t3 = new Thread(new DoAction());
//       t.start();
//       t2.start();
//       t3.start();
//        DoLock t = new DoLock();
//        DoLock t2 = new DoLock();
//        DoLock t3 = new DoLock();
//        t.start();
//        t2.start();
//        t3.start();
        DoReadWrite lock = new DoReadWrite();
        Thread t = new Thread(()->{
            lock.set();
        });
        Thread t2 = new Thread(()->{
            lock.get();
        });
        Thread t3 = new Thread(()->{
            lock.get();
        });
        t.start();
        t2.start();
        t3.start();
    }
}

class DoReadWrite{
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();
    private  int count = 1;


    public void  get(){
        readLock.lock();
        System.out.println("get"+count);
        System.out.println(Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + ":doGet()");
        readLock.unlock();
        set();
    }

    public void set(){
        writeLock.lock();
        count ++ ;
        System.out.println("set"+count + Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + ": doSet()");
        writeLock.unlock();
    }
}


class DoLock extends  Thread{
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + ":doGet()");
        set();
        lock.unlock();
    }

    public void set(){
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + ":doSet()");
        lock.unlock();
    }
}


class DoAction implements  Runnable {

    @Override
    public void run() {
        get();
    }

    public synchronized void get(){
        System.out.println(Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + ": doGet()");
        set();
    }

    public synchronized void set(){
        System.out.println(Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + ": doSet()");
    }
}