package com.demo.multidbdemo.product.config;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( basePackages = {"com.demo.multidbdemo.product.repo"},
				entityManagerFactoryRef = "productEntityManagerFactory",
				transactionManagerRef = "productTransactionManager")
public class ProductConfig {

	@Autowired
    private Environment env;
	
	@Bean(name = "productDataSource")
	@ConfigurationProperties(prefix = "db2.datasource")
	public DataSource productDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "productEntityManagerFactory")
	/*
	 * public LocalContainerEntityManagerFactoryBean productEntityManagerFactory(
	 * EntityManagerFactoryBuilder entityManagerFactoryBuilder,
	 * 
	 * @Qualifier("productDataSource") DataSource dataSource) {
	 */
	public LocalContainerEntityManagerFactoryBean productEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(productDataSource());
        em.setPackagesToScan(new String[]{"com.demo.multidbdemo.product.entity"});
        em.setPersistenceUnitName("productEntityManagerFactory");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("hibernate.show-sql",
                env.getProperty("jdbc.show-sql"));
        em.setJpaPropertyMap(properties);
        return em;
//		return entityManagerFactoryBuilder
//				.dataSource(dataSource)
//				.packages("com.demo.multidbdemo.product.entity")
//				.persistenceUnit("bankmonolith")
//				.build();
	}
	
	@Bean(name = "productTransactionManager")
	public PlatformTransactionManager productTransactionManager(
			@Qualifier("productEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
