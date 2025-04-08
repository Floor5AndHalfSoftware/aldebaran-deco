package com.aldebaran.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;









@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig
{
  @Autowired
  private Environment env;
  @Autowired
  private DataSource dataSource;
  @Autowired
  private LocalContainerEntityManagerFactoryBean entityManagerFactory;
  
  @Bean
  public DataSource dataSource() {
     DriverManagerDataSource dataSource = new DriverManagerDataSource();
     dataSource.setDriverClassName(this.env.getProperty("spring.datasource.driver-class-name"));
     dataSource.setUrl(this.env.getProperty("spring.datasource.url"));
     dataSource.setUsername(this.env.getProperty("spring.datasource.username"));
     dataSource.setPassword(this.env.getProperty("spring.datasource.password"));
     return (DataSource)dataSource;
  }



  
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
     LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    
     entityManagerFactory.setDataSource(this.dataSource);

    
     entityManagerFactory.setPackagesToScan(new String[] { this.env.getProperty("entitymanager.packagesToScan") });

    
     HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
     entityManagerFactory.setJpaVendorAdapter((JpaVendorAdapter)vendorAdapter);
    
     return entityManagerFactory;
  }



  
  @Bean
  public JpaTransactionManager transactionManager() {
     JpaTransactionManager transactionManager = new JpaTransactionManager();
     transactionManager.setEntityManagerFactory(this.entityManagerFactory.getObject());
     return transactionManager;
  }







  
  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
     return new PersistenceExceptionTranslationPostProcessor();
  }
}


