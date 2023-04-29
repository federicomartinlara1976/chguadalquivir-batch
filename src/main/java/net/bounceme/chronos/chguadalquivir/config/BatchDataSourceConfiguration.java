package net.bounceme.chronos.chguadalquivir.config;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;
import net.bounceme.chronos.chguadalquivir.model.BatchJobExecutionContext;
import net.bounceme.chronos.chguadalquivir.model.BatchJobExecutionParams;
import net.bounceme.chronos.chguadalquivir.model.BatchJobInstance;
import net.bounceme.chronos.chguadalquivir.model.BatchStepExecution;
import net.bounceme.chronos.chguadalquivir.model.BatchStepExecutionContext;

@Configuration
public class BatchDataSourceConfiguration {

	@Bean
	@Primary
	public DataSource dataSource(@Autowired BatchProperties c3P0Properties) throws PropertyVetoException {
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setDriverClass(c3P0Properties.getDriverClass());
		pooledDataSource.setUser(c3P0Properties.getUser());
		pooledDataSource.setPassword(c3P0Properties.getPassword());
		pooledDataSource.setJdbcUrl(c3P0Properties.getJdbcUrl());
		pooledDataSource.setMaxPoolSize(c3P0Properties.getMaxPoolSize());
		pooledDataSource.setMaxIdleTime(c3P0Properties.getMaxIdleTime());

		return pooledDataSource;
	}

	@Bean(name = "batchEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(
			@Qualifier("dataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		return builder.dataSource(dataSource)
				.packages(BatchJobExecution.class, BatchJobExecutionContext.class, BatchJobExecutionParams.class,
						BatchJobInstance.class, BatchStepExecution.class, BatchStepExecutionContext.class)
				.properties(properties)
				.persistenceUnit("batch-unit")
				.build();
	}

	@Bean(name = "batchTransactionManager")
	@Primary
	public PlatformTransactionManager batchTransactionManager(
			@Qualifier("batchEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
