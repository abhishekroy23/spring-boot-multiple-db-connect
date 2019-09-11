package com.demo.multidbdemo.shop.config;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( basePackages = {"com.demo.multidbdemo.shop.repo"},
				entityManagerFactoryRef = "shopDetailEntityManagerFactory",
				transactionManagerRef = "shopDetailTransactionManager")
public class ShopDetailConfig {

	@Primary
	@Bean(name = "shopDetailDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource shopDetailDataSource() {
		
		return DataSourceBuilder.create().build();
	}
	
	/*
	 * @Primary
	 * 
	 * @Bean(name = "shopDetailEntityManagerFactory") public
	 * LocalContainerEntityManagerFactoryBean shopDetailEntityManagerFactory(
	 * EntityManagerFactoryBuilder entityManagerFactoryBuilder,
	 * 
	 * @Qualifier("shopDetailDataSource") DataSource dataSource) { return
	 * entityManagerFactoryBuilder .dataSource(dataSource)
	 * .packages("com.demo.multidbdemo.shop.entity")
	 * .persistenceUnit("shopDetailEntityManagerFactory") .build(); }
	 */
	
	@Primary
	@Bean(name = "shopDetailEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean shopDetailEntityManagerFactory(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder,
			@Qualifier("shopDetailDataSource") DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(shopDetailDataSource());
        em.setPackagesToScan(new String[]{"com.demo.multidbdemo.shop.entity"});
        em.setPersistenceUnitName("productEntityManagerFactory");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
        
        em.setJpaPropertyMap(properties);
        return em;
		
	}
	
	@Primary
	@Bean(name = "shopDetailTransactionManager")
	public PlatformTransactionManager shopDetailTransactionManager(
			@Qualifier("shopDetailEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
