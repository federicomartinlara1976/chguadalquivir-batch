package net.bounceme.chronos.chguadalquivir.scheduler;

import java.util.Date;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableScheduling
public class PeriodicTasks {
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private JobLauncher jobLauncher;

	@Scheduled(cron = "${application.importJob.cron}")
    public void importJobTask() {
		try {
	        JobParametersBuilder builder = new JobParametersBuilder();
			builder.addDate("date", new Date());
			
			Job job = ctx.getBean("importJob", Job.class);
			
			JobExecution result = jobLauncher.run(job, builder.toJobParameters());
			
			// Exit on failure
			if (ExitStatus.FAILED.equals(result.getExitStatus())) {
				log.error("importJob failed!");
			}
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			log.error("ERROR:", e);
		}
    }
}
