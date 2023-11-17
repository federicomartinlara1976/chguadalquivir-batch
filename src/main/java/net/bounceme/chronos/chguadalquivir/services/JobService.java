package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import org.springframework.batch.core.JobInstance;

import net.bounceme.chronos.chguadalquivir.model.ExecutionResult;
import net.bounceme.chronos.chguadalquivir.model.batch.BatchJobExecution;

public interface JobService {
	
	/**
	 * @param name
	 * @throws Exception
	 */
	ExecutionResult run(String name) throws Exception;
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	JobInstance getLastJobInstance(String name);
	
	/**
	 * @param jobs 
	 * @return
	 */
	BatchJobExecution getLastJob(List<String> jobs);
	
	/**
	 * @param numJobs
	 * @param applicationJobs
	 * @return
	 */
	List<BatchJobExecution> getLastJobs(Integer numJobs, List<String> applicationJobs);
	
	/**
	 * @return
	 */
	List<String> getJobNames();
	
	/**
	 * @param name
	 * @return
	 */
	String getJobScheduling(String name);

	/**
	 * @return
	 */
	List<String> getAllJobs();
	
	/**
	 * @param jobInstanceId
	 * @return
	 */
	BatchJobExecution getJob(Long jobInstanceId);

	/**
	 * @param numExecutions
	 * @return
	 */
	List<BatchJobExecution> getLastExecutions(Integer numExecutions);
}
