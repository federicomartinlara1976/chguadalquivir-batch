package net.bounceme.chronos.chguadalquivir.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.beans.PropertyVetoException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = {"net.bounceme.chronos.chguadalquivir.repository"},
  entityManagerFactoryRef = "postgresEntityManagerFactory",
  transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDataSourceConfiguration {

	@Bean(name = "postgresDataSource")
	public DataSource postgresDataSource(@Autowired C3P0Properties c3P0Properties) throws PropertyVetoException {
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setDriverClass(c3P0Properties.getDriverClass());
		pooledDataSource.setUser(c3P0Properties.getUser());
		pooledDataSource.setPassword(c3P0Properties.getPassword());
		pooledDataSource.setJdbcUrl(c3P0Properties.getJdbcUrl());
		pooledDataSource.setMaxPoolSize(c3P0Properties.getMaxPoolSize());
		pooledDataSource.setMaxIdleTime(c3P0Properties.getMaxIdleTime());

		return pooledDataSource;
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
