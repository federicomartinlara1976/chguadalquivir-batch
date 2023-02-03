package net.bounceme.chronos.chguadalquivir.reader.mapping;

import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Execution;

@Component
public class StatExecutionsLineMapper extends DefaultLineMapper<Execution> implements InitializingBean {
	
	@Autowired
	private StatExecutionsLineTokenizer lineTokenizer;
	
	@Autowired
	private StatExecutionsFieldMapper fieldMapper;
	
	@Override
	public void afterPropertiesSet() {
		initialize();
	}
	
	private void initialize() {
		setLineTokenizer(lineTokenizer);
		setFieldSetMapper(fieldMapper);
	}
}
