package com.framework.web;

import com.framework.web.pojo.User;
import com.framework.web.webService.TestService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/10
 **/
public class WebServiceClient {

    public static void main(String[] args) {
        String address = "http://localhost:8080/services/test?wsdl";
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress(address);
        jaxWsProxyFactoryBean.setServiceClass(TestService.class);
        TestService testService = (TestService) jaxWsProxyFactoryBean.create();
        User result = testService.test("success");
        System.out.println(result);
    }
}
