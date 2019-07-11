package com.es.config;

import com.es.resp.CustomElasticsearchRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/11
 **/
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.es.resp", repositoryFactoryBeanClass = CustomElasticsearchRepositoryFactoryBean.class)
public class ESConfig {
}
