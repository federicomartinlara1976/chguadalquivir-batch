package net.bounceme.chronos.chguadalquivir.boot;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

import lombok.extern.slf4j.Slf4j;

//@SpringBootApplication(exclude={BatchAutoConfiguration.class})
@ImportResource({
    "classpath:applicationContext.xml",
	"classpath:importJob.xml",
	"classpath:lastExecutions.xml"
})
@Slf4j
public class TestGuadalquivirApplication implements CommandLineRunner {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("importJob")
	private Job importJob;
	
	@Autowired
	@Qualifier("lastExecutions")
	private Job lastExecutions;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(TestGuadalquivirApplication.class);
		builder.headless(false);
		builder.run(args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		executeJob(lastExecutions);
		
		System.exit(0);
	}
	
	/**
	 * @param job
	 */
	private void executeJob(Job job) {
		try {
	        JobParametersBuilder builder = new JobParametersBuilder();
			builder.addDate("date", new Date());
			
			JobExecution result = jobLauncher.run(job, builder.toJobParameters());
			
			// Exit on failure
			if (ExitStatus.FAILED.equals(result.getExitStatus())) {
				log.error("{} failed!", job.getName());
			}
			
			if (ExitStatus.COMPLETED.equals(result.getExitStatus())) {
				log.info("{} completed", job.getName());
			}
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			log.error("ERROR:", e);
		}
    }
}
