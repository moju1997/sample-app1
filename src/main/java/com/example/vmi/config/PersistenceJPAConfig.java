package com.example.vmi.config;

import java.util.Properties;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:datasource.properties")
@EnableJpaRepositories(basePackages = {"com.example.vmi.repository"})
public class PersistenceJPAConfig{
 
   private Logger logger = Logger.getLogger("PersistenceJPAConfig");
   
   @Autowired
   private Environment env;
   
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      logger.info("Inside entityManagerFactory() method");
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource());
      em.setPackagesToScan(new String[] { "com.example.vmi.entity" });
 
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(additionalProperties());
 
      return em;
   }
 
   @Bean
   public DataSource dataSource(){
      logger.info("Inside dataSource() method");
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
      dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
      dataSource.setUsername( env.getRequiredProperty("jdbc.username") );
      dataSource.setPassword( env.getRequiredProperty("jdbc.password") );
      return dataSource;
   }
 
   @Bean
   @Autowired
   public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
      logger.info("Inside transactionManager() method");
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(emf);
 
      return transactionManager;
   }

   Properties additionalProperties() {
      logger.info("Inside additionalProperties() method");
      Properties properties = new Properties();
      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
      properties.setProperty("hibernate.hbm2ddl.auto", "update");
      properties.setProperty("hibernate.show_sql", "true");
      properties.setProperty("hibernate.format_sql", "true");
      return properties;
   }

}
