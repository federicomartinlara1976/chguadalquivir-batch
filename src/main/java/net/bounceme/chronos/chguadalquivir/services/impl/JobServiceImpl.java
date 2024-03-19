package net.bounceme.chronos.chguadalquivir.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.SneakyThrows;
import net.bounceme.chronos.chguadalquivir.model.ExecutionResult;
import net.bounceme.chronos.chguadalquivir.services.JobService;
import net.bounceme.chronos.jpa.model.batch.BatchJobExecution;
import net.bounceme.chronos.jpa.repository.batch.BatchJobExecutionRepository;

@Service
public class JobServiceImpl implements JobService {
	
	private static final String PROPERTY_FORMAT = "application.%s.cron";

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobExplorer jobExplorer;

	@Autowired
	private Environment env;

	/**
	 * @param name
	 * @throws Exception
	 */
	@SneakyThrows
	public ExecutionResult run(String name) {
		try {
			JobParametersBuilder builder = new JobParametersBuilder();
			builder.addDate("date", new Date());

			Job job = ctx.getBean(name, Job.class);
			JobExecution result = jobLauncher.run(job, builder.toJobParameters());

			// Exit on failure
			if (ExitStatus.FAILED.equals(result.getExitStatus())) {
				return ExecutionResult.builder().exitStatus(ExitStatus.FAILED).message("La tarea ha fallado").build();
			} else {
				return ExecutionResult.builder().exitStatus(result.getExitStatus())
						.message(result.getExitStatus().getExitDescription()).build();
			}
		} catch (NoSuchBeanDefinitionException e) {
			throw new Exception("Tarea no encontrada");
		}
	}

	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public JobInstance getLastJobInstance(String name) {
		List<JobInstance> jobInstances = jobExplorer.getJobInstances(name, 0, 10);

		return (CollectionUtils.isNotEmpty(jobInstances)) ? jobInstances.get(0) : null;
	}

	/**
	 *
	 */
	@Override
	public List<String> getJobNames() {
		return jobExplorer.getJobNames();
	}

	@Override
	@SneakyThrows
	public String getJobScheduling(String name) {
		try {
			// Check if job exists
			Job job = ctx.getBean(name, Job.class);
			Assert.notNull(job, "job null");

			String property = String.format(PROPERTY_FORMAT, name);
			return env.getProperty(property);
		} catch (NoSuchBeanDefinitionException e) {
			throw new Exception("Tarea no encontrada");
		}
	}
	
	/**
	 * @return
	 */
	@Override
	public List<String> getAllJobs() {
        String[] allBeanNames = ctx.getBeanDefinitionNames();
        List<String> jobNames = new ArrayList<>();
        
        for(String beanName : allBeanNames) {
        	Object bean = ctx.getBean(beanName);
        	
        	if (bean instanceof Job) {
        		jobNames.add(beanName);
        	}
        }
        
        return jobNames;
    }
}
