package com.framework.web;

import javax.servlet.Filter;
import javax.servlet.FilterChain;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/07
 **/
public class TestFactory {

    public static void main(String[] args) {
        Factory factory = new AKFactory();
        Gun ak =  factory.produceGun();
        Bullet bullet = factory.produceBullet();
        ak.shooting();
        bullet.load();
        factory = new M4Factory();
        Gun m4 = factory.produceGun();
        Bullet m4Bullet = factory.produceBullet();
        m4.shooting();
        m4Bullet.load();
    }

}

interface Gun{
    void shooting();
}

interface Bullet{
    void load();
}

class AK implements Gun{

    @Override
    public void shooting() {
        System.out.println("AK shooting");
    }
}

class AKBullet implements Bullet{

    @Override
    public void load() {
        System.out.println("AK Reloading");
    }
}

class M4 implements Gun{

    @Override
    public void shooting() {
        System.out.println("M4 shooting");
    }
}

class M4_Bullet implements Bullet{

    @Override
    public void load() {
        System.out.println("M4 reloading");
    }
}

interface Factory{
    Gun produceGun();

    Bullet produceBullet();
}

class AKFactory implements Factory{
    @Override
    public Gun produceGun() {
        return new AK();
    }

    @Override
    public Bullet produceBullet() {
        return new AKBullet();
    }
}

class M4Factory implements Factory{

    @Override
    public Gun produceGun() {
        return new M4();
    }

    @Override
    public Bullet produceBullet() {
        return new M4_Bullet();
    }
}
