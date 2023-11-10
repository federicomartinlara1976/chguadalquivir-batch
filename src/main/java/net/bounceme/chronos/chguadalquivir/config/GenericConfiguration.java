package net.bounceme.chronos.chguadalquivir.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.validation.ValidatorService;
import net.bounceme.chronos.chguadalquivir.validation.impl.ValidatorServiceImpl;
import net.bounceme.chronos.chguadalquivir.writer.StringHeaderWriter;

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
	public ValidatorService<Embalse> embalseValidatorService() {
		return new ValidatorServiceImpl<>();
	}
}
