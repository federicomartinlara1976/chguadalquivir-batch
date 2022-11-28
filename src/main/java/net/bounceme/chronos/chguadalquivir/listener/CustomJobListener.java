package net.bounceme.chronos.chguadalquivir.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomJobListener implements JobExecutionListener {

	private Long startTime;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("JOBLISTENER: Se va a ejecutar el Job con ID: {}", jobExecution.getJobId());
		startTime = System.currentTimeMillis();
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		Long duration = System.currentTimeMillis() - startTime;
		log.info("JOBLISTENER: Se ha terminado de ejecutar el Job con ID: {}, ha tardado {} ms",
				jobExecution.getJobId(), duration);
	}

}