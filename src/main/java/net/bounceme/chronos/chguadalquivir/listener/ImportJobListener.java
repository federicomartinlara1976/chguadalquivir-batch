package net.bounceme.chronos.chguadalquivir.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
		
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			if (!Objects.isNull(alreadyExecuted) && Boolean.TRUE.equals(alreadyExecuted)) {
				log.error("La tarea ya ha sido ejecutada");
				jobExecution.setExitStatus(new ExitStatus("NOOP", "La tarea ya ha sido ejecutada"));
			}
		}
		else {
			jobExecution.setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
		}
	}
}
