package com.castis.adgateway.common;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "gatewayEntityManagerFactory"
        , transactionManagerRef = "gatewayTransactionManager"
        , basePackages = "com.castis.adgateway.repository"
)
@RequiredArgsConstructor
@EnableTransactionManagement
public class GatewayDatabaseConfig {
    private final Properties property;

    @Bean
    public DataSource gatewayDataSource() {
        BasicDataSource  dataSource = new BasicDataSource();
        dataSource.setDriverClassName(property.getDriverClassName());
        dataSource.setUrl(property.getGateway_url());
        dataSource.setUsername(property.getGateway_username());
        dataSource.setPassword(property.getGateway_password());
        dataSource.setInitialSize(10);
        dataSource.setMaxIdle(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(600000);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean gatewayEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName("gatewayEntityManager");
        em.setDataSource(gatewayDataSource());
        em.setPackagesToScan("com.castis.adgateway.model");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", property.getHibernateHbm2ddlAuto_AD());
        properties.put("hibernate.format_sql", property.getHibernateFormatSql());
        properties.put("hibernate.show_sql", property.getHibernateShowSql());
        properties.put("hibernate.dialect", property.getHibernateDialect());
        properties.put("hibernate.use_sql_comments", property.getHibernateUseSqlComments());
        properties.put("hibernate.id.new_generator_mappings", property.getHibernateIdNewGeneratorMappings());
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager gatewayTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(gatewayEntityManagerFactory().getObject());
        return transactionManager;
    }
}
