package net.bounceme.chronos.chguadalquivir.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.repository.ExecutionsRepository;

@Component
@Slf4j
public class ExecutionsItemReader implements ItemReader<Execution>, InitializingBean {

	@Autowired
	private ExecutionsRepository executionsRepository;

	private List<Execution> records;

	private Integer index = 0;

	@Override
	public void afterPropertiesSet() {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		try {

			index = 0;
		} catch (Exception e) {
			log.error("ERROR: ", e);
		}
	}

	/**
	 *
	 */
	@Override
	public Execution read() {
		Execution nextElement = null;

		if (index < records.size()) {
			nextElement = records.get(index);
			index++;
		} else {
			index = 0;
		}

		return nextElement;
	}

}
