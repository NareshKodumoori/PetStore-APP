package com.naresh.petstore.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.naresh.petstore.repository"}
	, entityManagerFactoryRef="petStoreEntityMgrFactory", transactionManagerRef="petStoreTransactionMgrFactory")
public class DatabaseConfig {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

	
	@Autowired
	private Environment env;
	
	private static final String PETSTORE_UNIT = "petStoreUnit";
	private static final String[] PACAKGES_TO_SCAN = {"com.naresh.petstore.model"};
	
	@Primary
	@Bean(name= "dataSource")
	public DataSource getDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		 dataSource.setUsername(env.getProperty("spring.datasource.username"));
		 dataSource.setPassword(env.getProperty("spring.datasource.password"));
		 dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		 dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		 dataSource.setMaximumPoolSize(Integer.valueOf(env.getProperty("spring.datasource.maxpool-size")));
		 logger.info("HikariDataSource is created successfully");
		 return dataSource;
	}
	
	@Primary
	@Bean("petStoreEntityMgrFactory")
	LocalContainerEntityManagerFactoryBean petStoreEntityMgrFactory(@Qualifier("dataSource") DataSource dataSource, Environment env) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(PACAKGES_TO_SCAN);
		entityManagerFactoryBean.setPersistenceUnitName(PETSTORE_UNIT);
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.default_schema", env.getProperty("hibernate.schema"));
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		
		logger.info("petStoreEntityMgrFactory is created successfully");
		return entityManagerFactoryBean;
	}

	@Primary
	@Bean("petStoreTransactionMgrFactory")
	JpaTransactionManager petStoreTransactionMgrFactory(@Qualifier("petStoreEntityMgrFactory") 
			EntityManagerFactory petStoreEntityMgrFactory){
		return new JpaTransactionManager(petStoreEntityMgrFactory);
	}
	
	
}
