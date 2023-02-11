package net.bounceme.chronos.chguadalquivir.services.impl;

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
import org.springframework.stereotype.Service;

import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;
import net.bounceme.chronos.chguadalquivir.repository.BatchJobExecutionRepository;
import net.bounceme.chronos.chguadalquivir.services.JobService;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobExplorer jobExplorer;
	
	@Autowired
	private BatchJobExecutionRepository batchJobExecutionRepository;

	/**
	 * @param name
	 * @throws Exception
	 */
	public void run(String name) throws Exception {
		try {
			JobParametersBuilder builder = new JobParametersBuilder();
			builder.addDate("date", new Date());

			Job job = ctx.getBean(name, Job.class);
			JobExecution result = jobLauncher.run(job, builder.toJobParameters());

			// Exit on failure
			if (ExitStatus.FAILED.equals(result.getExitStatus())) {
				throw new Exception("La tarea ha fallado");
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
	 * @return
	 */
	public BatchJobExecution getLastJob() {
		return batchJobExecutionRepository.getLastJobExecution();
	}

	/**
	 *
	 */
	@Override
	public List<String> getJobNames() {
		return jobExplorer.getJobNames();
	}
}
