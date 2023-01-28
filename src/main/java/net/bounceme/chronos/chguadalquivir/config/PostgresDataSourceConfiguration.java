package net.bounceme.chronos.chguadalquivir.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = {"net.bounceme.chronos.chguadalquivir.repository"},
  entityManagerFactoryRef = "postgresEntityManagerFactory",
  transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDataSourceConfiguration {

	@Bean
	@ConfigurationProperties("spring.datasource.postgres")
	public DataSourceProperties postgresDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "postgresDataSource")
	public DataSource postgresDataSource() {
		return postgresDataSourceProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
      @Qualifier("postgresDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder) {
        return builder
          .dataSource(dataSource)
          .packages(ExecutionStats.class)
          .build();
    }

    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager postgresTransactionManager(
      @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
