package com.bind.ptw.be.initializers;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.bind.ptw.be.entities")
@PropertySource("file:${prop.path}/app.properties")
public class HibernateConfiguration {
	
	
	@Autowired
	private Environment env;
	
	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}

	@SuppressWarnings("deprecation")
	@Bean
	public SessionFactory sessionFactory() {

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(getDataSource());
		sessionFactoryBean.setPackagesToScan("com.bind.ptw.be.entities");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		hibernateProperties.put("hibernate.show_sql", false);
		//hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.put("connection.autoReconnect", true);
		hibernateProperties.put("connection.autoReconnectForPools", true);
		hibernateProperties.put("connection.is-connection-validation-required", true);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		// return (SessionFactory) sessionFactoryBean.get;

		return new LocalSessionFactoryBuilder(getDataSource()).scanPackages("com.bind.ptw.be.entities").addProperties(hibernateProperties)
				.buildSessionFactory();
	}

  @Bean
  public DataSource getDataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    System.out.println("DB UR: " + env.getProperty("db.dbURL"));
    System.out.println("DB Port: " + env.getProperty("db.port"));
    System.out.println("DB Name: " + env.getProperty("db.dbname"));
    System.out.println("User Name: " + env.getProperty("db.port"));
    System.out.println("Password: " + env.getProperty("db.password"));
    dataSource.setUrl("jdbc:mysql://"+ env.getProperty("db.dbURL") + ":"+ env.getProperty("db.port")+"/"+env.getProperty("db.dbname"));;
    dataSource.setUsername(env.getProperty("db.username"));
    dataSource.setPassword(env.getProperty("db.password"));
    return dataSource;
  }

  @Bean
  public HibernateTransactionManager hibTransMan() {
    return new HibernateTransactionManager(sessionFactory());
  }


}


