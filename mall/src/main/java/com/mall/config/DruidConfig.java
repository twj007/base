//package com.mall.config;
//
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.support.TransactionTemplate;
//import javax.sql.DataSource;
//
///***
// **@project: base
// **@description: 多数据源配置（考虑通过aop的方式实现）
// **@Author: twj
// **@Date: 2019/06/18
// * 方式一： 通过不同的配置类，对应不同的mapper文件创建一组不同的datasource，sqlSessionFactory， transactionManager
// * 方式二： 通过注解标注数据源类型，通过aop切换不同的数据源
// * 这里是方式一
// **/
//@Configuration
//@MapperScan(basePackages = "com.mall.dao.local", sqlSessionTemplateRef = "masterSqlSessionTemplate")
//public class DruidConfig {
//
//    @Value("${spring.datasource.druid.master.url}")
//    private String mUrl;
//
//    @Value("${spring.datasource.druid.master.username}")
//    private String mUsername;
//
//    @Value("${spring.datasource.druid.master.password}")
//    private String mPassword;
//
//    @Value("${spring.datasource.druid.slaver.driver-class-name}")
//    private String mDriver;
//
//
//    @Bean(name = "masterDatasource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
//    @Primary
//    public DataSource dataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(mDriver);
//        dataSource.setUrl(mUrl);
//        dataSource.setUsername(mUsername);
//        dataSource.setPassword(mPassword);
//        return dataSource;
//    }
//
//
//    @Bean(name = "masterSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
//        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/local/*.xml"));
//        factory.setTypeAliasesPackage("com.mall.pojo");
//        factory.setDataSource(dataSource());
//        return factory.getObject();
//    }
//
//    @Bean(name = "masterTransactionTemplate")
//    @Primary
//    public TransactionTemplate transactionTemplate(@Qualifier("masterDatasourceTransactionManager") DataSourceTransactionManager manager){
//        TransactionTemplate template = new TransactionTemplate();
//        template.setTransactionManager(manager);
//        return template;
//    }
//
//    @Bean(name = "masterDatasourceTransactionManager")
//    @Primary
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("masterDatasource")DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "masterSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate template(@Qualifier("masterSqlSessionFactory") SqlSessionFactory factory){
//        return new SqlSessionTemplate(factory);
//    }
//
//
//
//
//}
