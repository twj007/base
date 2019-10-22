package com.framework.web.script;

import groovy.lang.Script;

import java.util.Timer;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/09
 **/
public class TestScript extends Script {
    @Override
    public Object run() {
        return "ok";
    }

    public Object run2(){
        return "ok";
    }

    static void main(String[] args){
        Timer timer = new Timer();

    }
}
