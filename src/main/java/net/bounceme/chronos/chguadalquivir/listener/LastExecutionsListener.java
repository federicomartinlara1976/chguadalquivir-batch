package net.bounceme.chronos.chguadalquivir.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LastExecutionsListener extends AbstractListener {
	
	/**
	 *
	 */
	protected void initializeConfig(JobExecution jobExecution) {
		log.info("initializeConfig");
	}

	@Override
	protected void updateStatus(JobExecution jobExecution) {
		jobExecution.setExitStatus(ExitStatus.COMPLETED);
	}
}
