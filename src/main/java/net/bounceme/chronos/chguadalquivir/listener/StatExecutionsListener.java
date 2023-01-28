package net.bounceme.chronos.chguadalquivir.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Execution;

@Component
public class StatExecutionsListener extends AbstractListener {
	
	/**
	 *
	 */
	protected void initializeConfig(JobExecution jobExecution) {
		List<Execution> executions = new ArrayList<>();
		
		jobExecution.getExecutionContext().put("EXECUTIONS", executions);
	}

}
