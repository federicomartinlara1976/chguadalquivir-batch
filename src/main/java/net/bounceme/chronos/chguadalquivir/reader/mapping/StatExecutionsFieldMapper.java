package net.bounceme.chronos.chguadalquivir.reader.mapping;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Execution;

@Component
public class StatExecutionsFieldMapper extends BeanWrapperFieldSetMapper<Execution> {

	public StatExecutionsFieldMapper() {
		super();
		setTargetType(Execution.class);
	}
	
}
