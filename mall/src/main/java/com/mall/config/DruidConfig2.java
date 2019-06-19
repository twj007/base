//package com.mall.config;
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
// **@description: config type
// **@Author: twj
// **@Date: 2019/06/19
// **/
//@Configuration
//@MapperScan(basePackages = "com.mall.dao.remote", sqlSessionTemplateRef = "slaverSqlSessionTemplate")
//public class DruidConfig2 {
//
//    @Value("${spring.datasource.druid.slaver.url}")
//    private String sUrl;
//
//    @Value("${spring.datasource.druid.slaver.username}")
//    private String sUsername;
//
//    @Value("${spring.datasource.druid.slaver.password}")
//    private String sPassword;
//
//    @Value("${spring.datasource.druid.slaver.driver-class-name}")
//    private String sDriver;
//
//    @Bean(name = "slaverDatasource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.slaver")
//    public DataSource sDataSource(){
//        DruidDataSource slaverDatasource = new DruidDataSource();
//        slaverDatasource.setDriverClassName(sDriver);
//        slaverDatasource.setUrl(sUrl);
//        slaverDatasource.setUsername(sUsername);
//        slaverDatasource.setPassword(sPassword);
//        return slaverDatasource;
//    }
//
//
//    @Bean(name = "slaverSqlSessionFactory")
//    public SqlSessionFactory sSqlSessionFactory(@Qualifier("slaverDatasource")DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setTypeAliasesPackage("com.mall.pojo");
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/remote/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "slaverTransactionTemplate")
//    public TransactionTemplate sTransactionTemplate(@Qualifier("slaverDatasourceTransactionManager")DataSourceTransactionManager manager){
//        TransactionTemplate template = new TransactionTemplate();
//        template.setTransactionManager(manager);
//        return template;
//    }
//
//    @Bean(name = "slaverDatasourceTransactionManager")
//    public DataSourceTransactionManager sDataSourceTransactionManager(@Qualifier("slaverDatasource")DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "slaverSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate template(@Qualifier("slaverSqlSessionFactory") SqlSessionFactory factory){
//        return new SqlSessionTemplate(factory);
//    }
//}
