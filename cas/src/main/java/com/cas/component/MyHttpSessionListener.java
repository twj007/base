package com.cas.component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/23
 **/
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    private static Integer count;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("new session create");
        count++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("session destroy");
        count--;
    }
}
