package net.bounceme.chronos.chguadalquivir.reader;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.repository.ExecutionsRepository;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;

@Component
@StepScope
@Slf4j
public class ExecutionsItemReader implements ItemReader<Execution>, InitializingBean {
	
	@Autowired
	private JobExecution jobExecution;
	
	@Autowired
	private CHGuadalquivirHelper helper;

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
			Date currentDate = (Date) jobExecution.getExecutionContext().get("CURRENT_DATE");
			Date beforeDate = helper.subtractDays(currentDate, 5);
			
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
