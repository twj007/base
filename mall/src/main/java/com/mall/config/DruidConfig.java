//package com.mall.config;
//
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Qualifier;
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
// **/
//@Configuration
//public class DruidConfig {
//
////    @Value("${spring.datasource.druid.master.url}")
////    private String mUrl;
////
////    @Value("${spring.datasource.druid.master.username}")
////    private String mUsername;
////
////    @Value("${spring.datasource.druid.master.password}")
////    private String mPassword;
////
////    @Value("${spring.datasource.druid.slaver.driver}")
////    private String mDriver;
////
////    @Value("${spring.datasource.druid.slaver.url}")
////    private String sUrl;
////
////    @Value("${spring.datasource.druid.slaver.username}")
////    private String sUsername;
////
////    @Value("${spring.datasource.druid.slaver.password}")
////    private String sPassword;
////
////    @Value("${spring.datasource.druid.slaver.driver}")
////    private String sDriver;
//
//    @Bean(name = "masterDatasource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
//    @Primary
//    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//
//    @Bean(name = "masterSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
//        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.mapper"));
//        factory.setTypeAliasesPackage("com.mall.pojo");
//        factory.setDataSource(dataSource());
//        return factory.getObject();
//    }
//
//    @Bean(name = "masterTransactionTemplate")
//    @Primary
//    public TransactionTemplate transactionTemplate(){
//        TransactionTemplate template = new TransactionTemplate();
//        template.setTransactionManager(DataSourceTransactionManager());
//        return template;
//    }
//
//    @Bean(name = "masterDatasourceTransactionManager")
//    @Primary
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("masterDatasource")DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//
//    @Bean(name = "slaverDatasource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.slaver")
//    @Primary
//    public DataSource sDataSource(){
//
//    }
//
//
//    @Bean(name = "slaverSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sSqlSessionFactory(){
//
//    }
//
//    @Bean(name = "slaverTransactionTemplate")
//    @Primary
//    public TransactionTemplate sTransactionTemplate(){
//
//    }
//
//    @Bean(name = "slaverDatasourceTransactionManager")
//    @Primary
//    public DataSourceTransactionManager sDataSourceTransactionManager(){
//
//    }
//
//
//
//}
