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
	
	@Bean
    public ItemWriter<Execution> executionsWriter(Environment environment, SimpleDateFormat dateFormat) {
        String exportFilePath = environment.getRequiredProperty("application.lastExecutions.export.file.path");
        String suffix = environment.getRequiredProperty("application.lastExecutions.export.file.suffix");
        
        Date date = new Date();
        String sDate = dateFormat.format(date);
        Resource exportFileResource = new FileSystemResource(exportFilePath + "-" + sDate + "." + suffix);

        String exportFileHeader = environment.getRequiredProperty("application.lastExecutions.export.file.header");
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);

        LineAggregator<Execution> lineAggregator = createExecutionLineAggregator();

        return new FlatFileItemWriterBuilder<Execution>()
                .name("executionsWriter")
                .headerCallback(headerWriter)
                .lineAggregator(lineAggregator)
                .resource(exportFileResource)
                .build();
    }
	
	private LineAggregator<Execution> createExecutionLineAggregator() {
        DelimitedLineAggregator<Execution> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        FieldExtractor<Execution> fieldExtractor = createExecutionFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<Execution> createExecutionFieldExtractor() {
        BeanWrapperFieldExtractor<Execution> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"id", "value", "executionTime"});
        return extractor;
    }
}
