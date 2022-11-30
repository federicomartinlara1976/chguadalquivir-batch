package net.bounceme.chronos.chguadalquivir;

import java.util.Date;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude={BatchAutoConfiguration.class})
@ImportResource({
    "classpath:applicationContext.xml",
    "classpath:importJob.xml"
})
@Slf4j
public class ChGuadalquivirApplicationCmd implements ApplicationRunner {
	
	@Value("${spring.batch.job.names}")
	private String jobId;
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private JobLauncher jobLauncher;

	public static void main(String[] args) {
		SpringApplication.run(ChGuadalquivirApplicationCmd.class, args);
		
		// If completed, exit normally
		System.exit(0);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addDate("date", new Date());
		
		Job job = ctx.getBean(jobId, Job.class);
		
		log.info("Executing job {}", jobId);
		JobExecution result = jobLauncher.run(job, builder.toJobParameters());
		
		// Exit on failure
		if (ExitStatus.FAILED.equals(result.getExitStatus())) {
			System.exit(1);
		}
	}

}
