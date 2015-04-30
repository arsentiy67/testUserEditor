package com.github.arsentiy67.usereditor.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatabaseConfig {
	
	@Bean
    public static PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ClassPathResource[] resources = new ClassPathResource[ ]
			{ new ClassPathResource( "/com/github/arsentiy67/usereditor/config/db.properties" ) };
		ppc.setLocations( resources );
		ppc.setIgnoreUnresolvablePlaceholders( true );
		return ppc;
    }
	
	@Value( "${jdbc.url}" ) private String jdbcUrl;
    @Value( "${jdbc.driverClassName}" ) private String driverClassName;
    @Value( "${jdbc.username}" ) private String username;
    @Value( "${jdbc.password}" ) private String password;
	
	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	@Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
	
	@Bean
    public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.github.arsentiy67.usereditor.model")
        	.addProperties(getHibernateProperties());
        return builder.buildSessionFactory();
    }
	
	private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return prop;
	}

}
