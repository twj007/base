package com.framework.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 **@project: base
 **@description: ali pay configuration
 **@Author: twj
 **@Date: 2019/04/24
 **/
@Configuration
public class AlipayConfiguration {

    @Value("${alipay.gatewayUrl}")
    String gatewayUrl;

    @Value("${alipay.appId}")
    String appid;

    @Value("${alipay.appPrivateKey}")
    String appPrivateKey;

    @Value("${alipay.formate}")
    String formate;

    @Value("${alipay.charset}")
    String charset;

    @Value("${alipay.return.publicKey}")
    String publicKey;

    @Value("${alipay.signType}")
    String signType;

    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(gatewayUrl,
                appid,
                appPrivateKey,
                formate,
                charset,
                publicKey,
                signType);
    }
}
