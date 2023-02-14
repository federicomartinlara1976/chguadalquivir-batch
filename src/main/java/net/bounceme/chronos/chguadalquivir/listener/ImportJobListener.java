package net.bounceme.chronos.chguadalquivir.listener;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

@Component
public class ImportJobListener extends AbstractListener {
	
	/**
	 *
	 */
	protected void initializeConfig(JobExecution jobExecution) {
		Map<String, Long> stepTimes = new HashMap<>();
		
		jobExecution.getExecutionContext().put("STEP_TIMES", stepTimes);
	}

	@Override
	protected void updateStatus(JobExecution jobExecution) {
		Boolean alreadyExecuted = (Boolean) jobExecution.getExecutionContext().get("ALREADY_EXECUTED");
		
		if (jobExecution.getStatus() == BatchStatus.COMPLETED && alreadyExecuted) {
			jobExecution.setExitStatus(ExitStatus.NOOP);
		}
		else {
			jobExecution.setExitStatus(ExitStatus.COMPLETED);
		}
	}
}
