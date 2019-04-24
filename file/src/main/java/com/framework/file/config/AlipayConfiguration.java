//package com.framework.file.config;
//
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///***
// **@project: base
// **@description: alipay config
// **@Author: twj
// **@Date: 2019/04/11
// **/
//@Configuration
//public class AlipayConfiguration {
//
//    @Value("${alipay.gatewayUrl}")
//    String gatewayUrl;
//
//    @Value("${alipay.appid}")
//    String appid;
//
//    @Value("${alipay.appPrivateKey}")
//    String appPrivateKey;
//
//    @Value("${alipay.formate}")
//    String formate;
//
//    @Value("${alipay.charset}")
//    String charset;
//
//    @Value("${alipay.publicKey}")
//    String publicKey;
//
//    @Value("${alipay.signType}")
//    String signType;
//
//    @Bean
//    public AlipayClient alipayClient(){
//
//        return new DefaultAlipayClient(gatewayUrl,
//                appid,
//                appPrivateKey,
//                formate,
//                charset,
//                publicKey,
//                signType);
//    }
//
//}
