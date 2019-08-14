package com.oauth2.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/01
 **/
@Configuration
public class JDBCConfig {

    private static final Logger logger = LoggerFactory.getLogger(JDBCConfig.class);

    @Value("${spring.datasource.druid.slaver.url}")
    private String sUrl;

    @Value("${spring.datasource.druid.slaver.username}")
    private String sUsername;

    @Value("${spring.datasource.druid.slaver.password}")
    private String sPassword;

    @Value("${spring.datasource.druid.slaver.driver-class-name}")
    private String sDriver;

    @Value("${spring.datasource.druid.master.url}")
    private String mUrl;

    @Value("${spring.datasource.druid.master.username}")
    private String mUsername;

    @Value("${spring.datasource.druid.master.password}")
    private String mPassword;

    @Value("${spring.datasource.druid.slaver.driver-class-name}")
    private String mDriver;

    @Bean("datasource")
    public DataSource masterDataSource(){
        logger.debug("【jdbcConfig】: 初始化master数据源");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(mDriver);
        dataSource.setUrl(mUrl);
        dataSource.setUsername(mUsername);
        dataSource.setPassword(mPassword);
        return dataSource;
    }



//    @Bean
//    public PageHelper pageHelper() {
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//        p.setProperty("dialect", "Mysql");
//        p.setProperty("offsetAsPageNum", "true");
//        p.setProperty("rowBoundsWithCount", "true");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("datasource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        bean.setTypeAliasesPackage("com.oauth2.server.admin.model");
        //bean.setPlugins(new Interceptor[]{new PageInterceptor()});
        bean.setDataSource(dataSource);
        return bean.getObject();
    }


    @Bean("transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("datasource")DataSource dataSource){
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return  manager;
    }
}
