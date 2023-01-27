package net.bounceme.chronos.chguadalquivir.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bounceme.chronos.chguadalquivir.support.Constants;

@Configuration
public class GenericConfiguration {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public SimpleDateFormat dateFormat() {
		return new SimpleDateFormat(Constants.DATE_FORMAT);
	}
}
