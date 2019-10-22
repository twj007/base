package com.cas.configuration;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/12
 **/
@Configuration
public class WSConfig {

    private static final String ADDRESS = "http://localhost:8080/services/test?wsdl";

    @Bean
    JaxWsProxyFactoryBean jaxWsPortProxyFactoryBean(){
        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
        bean.setAddress(ADDRESS);
        return bean;
    }

//    @Bean
//    TestService testService(@Qualifier JaxWsProxyFactoryBean bean){
//        bean.setServiceClass(TestService.class);
//        return (TestService)bean.create();
//    }

}
