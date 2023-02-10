package net.bounceme.chronos.chguadalquivir.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;
import net.bounceme.chronos.chguadalquivir.model.BatchJobExecutionContext;
import net.bounceme.chronos.chguadalquivir.model.BatchJobExecutionParams;
import net.bounceme.chronos.chguadalquivir.model.BatchJobInstance;
import net.bounceme.chronos.chguadalquivir.model.BatchStepExecution;
import net.bounceme.chronos.chguadalquivir.model.BatchStepExecutionContext;

@Configuration
public class BatchDataSourceConfiguration {

	@Bean
	@ConfigurationProperties("spring.datasource.batch")
	public DataSourceProperties batchDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	public DataSource dataSource() {
		return batchDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean(name = "batchEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(
			@Qualifier("dataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource)
				.packages(BatchJobExecution.class, BatchJobExecutionContext.class, BatchJobExecutionParams.class,
						BatchJobInstance.class, BatchStepExecution.class, BatchStepExecutionContext.class)
				.build();
	}

	@Bean(name = "batchTransactionManager")
	@Primary
	public PlatformTransactionManager batchTransactionManager(
			@Qualifier("batchEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
