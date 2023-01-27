package net.bounceme.chronos.chguadalquivir.config;

import java.text.SimpleDateFormat;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.support.Constants;
import net.bounceme.chronos.chguadalquivir.validation.ValidatorService;
import net.bounceme.chronos.chguadalquivir.validation.impl.ValidatorServiceImpl;

@Configuration
@EnableBatchProcessing
public class GenericConfiguration {

	@Bean
	@Scope("prototype")
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	@Scope("prototype")
	public SimpleDateFormat dateFormat() {
		return new SimpleDateFormat(Constants.DATE_FORMAT);
	}
	
	@Bean
	@Scope("prototype")
	public ValidatorService<Embalse> embalseValidatorService() {
		return new ValidatorServiceImpl<Embalse>();
	}
}
