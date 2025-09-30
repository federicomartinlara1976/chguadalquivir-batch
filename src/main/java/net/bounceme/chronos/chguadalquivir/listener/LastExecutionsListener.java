package net.bounceme.chronos.chguadalquivir.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LastExecutionsListener extends AbstractListener {
	
	/**
	 *
	 */
	protected void initializeConfig(JobExecution jobExecution) {
		Assert.notNull(jobExecution, "La ejecución no debe ser nula");
		log.info("initializeConfig");
	}

	@Override
	protected void updateStatus(JobExecution jobExecution) {
		Assert.notNull(jobExecution, "La ejecución no debe ser nula");
		jobExecution.setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
	}
}
