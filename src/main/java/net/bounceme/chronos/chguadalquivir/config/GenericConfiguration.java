package net.bounceme.chronos.chguadalquivir.config;

import java.text.SimpleDateFormat;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.validation.ValidatorService;
import net.bounceme.chronos.chguadalquivir.validation.impl.ValidatorServiceImpl;

@Configuration
@EnableBatchProcessing
public class GenericConfiguration {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	@Bean
	@Scope("prototype")
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	@Scope("prototype")
	public SimpleDateFormat dateFormat() {
		return new SimpleDateFormat(DATE_FORMAT);
	}
	
	@Bean
	@Scope("prototype")
	public ValidatorService<RegistroDiarioEmbalse> embalseValidatorService() {
		return new ValidatorServiceImpl<>();
	}
}
