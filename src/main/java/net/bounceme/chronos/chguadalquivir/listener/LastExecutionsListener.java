package net.bounceme.chronos.chguadalquivir.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

@Component
public class LastExecutionsListener extends AbstractListener {
	
	/**
	 *
	 */
	protected void initializeConfig(JobExecution jobExecution) {
		jobExecution.getExecutionContext().put("CURRENT_DATE", new Date());
	}

}
