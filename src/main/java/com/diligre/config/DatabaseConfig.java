package com.diligre.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.diligre.repository")
@PropertySources({
        @PropertySource("classpath:database.properties"),
        @PropertySource("classpath:hikariCP.properties"),
        @PropertySource("classpath:hibernate.properties")
})
@ComponentScan(basePackages = "com.diligre.config")
public class DatabaseConfig {

    @Value("org.postgresql.Driver")
    private String dbDriver;

    @Value("jdbc:postgresql://ec2-79-125-26-232.eu-west-1.compute.amazonaws.com:5432/dm5meh3v2tj98")
    private String dbConnectionUrl;

    @Value("lptvqmljojllox")
    private String dbUser;

    @Value("87ef4bbc1660b3c22d01119874a21c0989b544a71e3c8c5f7515fcac07eef322")
    private String dbPassword;

    @Value("60000")
    private Integer maxLifetime;

    @Value("45000")
    private Integer idleTimeout;

    @Value("50")
    private Integer maximumPoolSize;

    @Value("true")
    private String cacheServerConfiguration;

    @Value("2048")
    private String prepStmtCacheSqlLimit;

    @Value("false")
    private String alwaysSendSetIsolation;

    @Value("true")
    private String cachePrepStmts;

    @Value("false")
    private String maintainTimeStats;

    @Value("250")
    private String prepStmtCacheSize;

    @Value("true")
    private String allowMultiQueries;

    @Value("org.hibernate.dialect.PostgreSQLDialect")
    private String hibernateDialect;

    @Value("update")
    private String hbm2ddl;

    @Value("true")
    private String showSQL;

    @Value("true")
    private String formatSQL;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(this.dbDriver);
        hikariConfig.setJdbcUrl(this.dbConnectionUrl);
        hikariConfig.setUsername(this.dbUser);
        hikariConfig.setPassword(this.dbPassword);
        hikariConfig.setMaxLifetime(this.maxLifetime);
        hikariConfig.setIdleTimeout(this.idleTimeout);
        hikariConfig.setMaximumPoolSize(this.maximumPoolSize);
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", this.cacheServerConfiguration);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", this.prepStmtCacheSqlLimit);
        hikariConfig.addDataSourceProperty("alwaysSendSetIsolation", this.alwaysSendSetIsolation);
        hikariConfig.addDataSourceProperty("cachePrepStmts", this.cachePrepStmts);
        hikariConfig.addDataSourceProperty("maintainTimeStats", this.maintainTimeStats);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", this.prepStmtCacheSize);
        hikariConfig.addDataSourceProperty("allowMultiQueries", this.allowMultiQueries);
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPackagesToScan("com.diligre.entity");
        entityManagerFactoryBean.setDataSource(this.dataSource());
        entityManagerFactoryBean.setJpaProperties(this.getJpaProperties());
        entityManagerFactoryBean.setJpaVendorAdapter(this.getHibernateVendorAdapter());
        return entityManagerFactoryBean;
    }

    @Bean
    public Properties getJpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", this.hibernateDialect);
        jpaProperties.put("hibernate.hbm2ddl.auto", this.hbm2ddl);
        jpaProperties.put("hibernate.show_sql", this.showSQL);
        jpaProperties.put("hibernate.format_sql", this.formatSQL);
        return jpaProperties;
    }

    @Bean
    public JpaVendorAdapter getHibernateVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

}

