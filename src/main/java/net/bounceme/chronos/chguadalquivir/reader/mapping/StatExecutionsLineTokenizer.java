package net.bounceme.chronos.chguadalquivir.reader.mapping;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class StatExecutionsLineTokenizer extends DelimitedLineTokenizer implements InitializingBean {
	
	@Override
	public void afterPropertiesSet() {
		initialize();
	}
	
	private void initialize() {
		String[] names = new String[] { "id", "value", "executionTime" };
		setNames(names);
	}
}
