package com.framework.web.config;

import com.framework.web.webService.testServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/10
 **/
@Configuration
public class WebServiceConfiguration {

    @Autowired
    private Bus bus;

    @Bean
    Endpoint endpoint(){
        Endpoint endPoint = new EndpointImpl(bus, new testServiceImpl());
        endPoint.publish("/test");
        return endPoint;
    }

}
