package net.bounceme.chronos.chguadalquivir.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
}
