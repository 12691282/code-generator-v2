package com.gamma.config;



import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * MyBatis基础配置
 *
 */
//@Configuration
//@EnableTransactionManagement
//public class MySqlDataBaseConfig implements TransactionManagementConfigurer {
//
//	  // HikariCP
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//
//    @Value("${spring.datasource.pool-name}")
//    private String poolName;
//
//    @Value("${spring.datasource.connection-timeout}")
//    private int connectionTimeout;
//
//    @Value("${spring.datasource.max-lifetime}")
//    private int maxLifetime;
//
//    @Value("${spring.datasource.maximum-pool-size}")
//    private int maximumPoolSize;
//
//    @Value("${spring.datasource.minimum-idle}")
//    private int minimumIdle;
//
//    @Value("${spring.datasource.idle-timeout}")
//    private int idleTimeout;
//
//    @Value("${spring.datasource.validation-query}")
//    private String validationQuery;
//
//    @Value("${spring.datasource.pool-prepared-statements}")
//    private String cachePrepStmts;
//
//    @Value("${spring.datasource.pool-prepared-statements-cache-size}")
//    private String prepStmtCacheSize;
//
//    @Value("${spring.datasource.pool-prepared-statements-cache-sql-limit}")
//    private String prepStmtCacheSqlLimit;
//
//    @Value("${spring.datasource.user-server-prepared-statements}")
//    private String useServerPrepStmts;
//	
//
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactoryBean() {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(primaryDataSource());
//        bean.setTypeAliasesPackage("com.alpha.mybaits.model");
//
//
//        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    @Bean
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return new DataSourceTransactionManager(primaryDataSource());
//    }
//    
//    @Bean
//    public DataSource primaryDataSource() {
//
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName(driverClassName);
//        hikariConfig.setJdbcUrl(url); 
//        hikariConfig.setUsername(username);
//        hikariConfig.setPassword(password);
//        hikariConfig.setMaximumPoolSize(maximumPoolSize);
//        hikariConfig.setMinimumIdle(minimumIdle);
//        hikariConfig.setConnectionTimeout(connectionTimeout);
//        hikariConfig.setIdleTimeout(idleTimeout);
//        hikariConfig.setConnectionTestQuery(validationQuery);
//        hikariConfig.setPoolName("springHikariCP");
//
//        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", cachePrepStmts);
//        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", prepStmtCacheSize);
//        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
//        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", useServerPrepStmts);
//
//        HikariDataSource ds = new HikariDataSource(hikariConfig);
//        return ds;
//    }
//}
