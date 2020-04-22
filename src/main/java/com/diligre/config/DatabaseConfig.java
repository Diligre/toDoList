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

    @Value("${database.driver}")
    private String dbDriver;

    @Value("${database.url}")
    private String dbConnectionUrl;

    @Value("${database.user}")
    private String dbUser;

    @Value("${database.password}")
    private String dbPassword;

    @Value("${hikari.datasource.setMaxLifetime}")
    private Integer maxLifetime;

    @Value("${hikari.datasource.setIdleTimeout}")
    private Integer idleTimeout;

    @Value("${hikari.datasource.setMaximumPoolSize}")
    private Integer maximumPoolSize;

    @Value("${hikari.datasource.cacheServerConfiguration}")
    private String cacheServerConfiguration;

    @Value("${hikari.datasource.prepStmtCacheSqlLimit}")
    private String prepStmtCacheSqlLimit;

    @Value("${hikari.datasource.alwaysSendSetIsolation}")
    private String alwaysSendSetIsolation;

    @Value("${hikari.datasource.cachePrepStmts}")
    private String cachePrepStmts;

    @Value("${hikari.datasource.maintainTimeStats}")
    private String maintainTimeStats;

    @Value("${hikari.datasource.prepStmtCacheSize}")
    private String prepStmtCacheSize;

    @Value("${hikari.datasource.allowMultiQueries}")
    private String allowMultiQueries;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

    @Value("${hibernate.show_sql}")
    private String showSQL;

    @Value("${hibernate.format_sql}")
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

