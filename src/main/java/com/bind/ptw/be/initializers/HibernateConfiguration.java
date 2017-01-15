package com.bind.ptw.be.initializers;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.bind.ptw.be.entities")
@PropertySource(value = {"classpath:application.properties"})
public class HibernateConfiguration {
  @Bean
  public HibernateTemplate hibernateTemplate() {
    return new HibernateTemplate(sessionFactory());
  }

  @Bean
  public SessionFactory sessionFactory() {

    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(getDataSource());
    sessionFactoryBean.setPackagesToScan("com.bind.ptw.be.entities");
    Properties hibernateProperties = new Properties();
    hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
    hibernateProperties.put("hibernate.show_sql", true);
    hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
    sessionFactoryBean.setHibernateProperties(hibernateProperties);

    // return (SessionFactory) sessionFactoryBean.get;

    return new LocalSessionFactoryBuilder(getDataSource()).scanPackages("com.bind.ptw.be.entities").addProperties(hibernateProperties)
        .buildSessionFactory();
  }

  @Bean
  public DataSource getDataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/ptwdev");
    dataSource.setUsername("root");
    dataSource.setPassword("root");


    return dataSource;
  }

  @Bean
  public HibernateTransactionManager hibTransMan() {
    return new HibernateTransactionManager(sessionFactory());
  }


}


