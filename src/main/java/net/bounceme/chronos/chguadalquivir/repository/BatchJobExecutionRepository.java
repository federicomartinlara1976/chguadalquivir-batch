package net.bounceme.chronos.chguadalquivir.repository;

import java.util.List;

import net.bounceme.chronos.chguadalquivir.model.batch.BatchJobExecution;

public interface BatchJobExecutionRepository {

	/**
	 * @param applicationJobs 
	 * @return
	 */
	BatchJobExecution getLastJobExecution(List<String> applicationJobs);
	
	/**
	 * @param numResults
	 * @param applicationJobs
	 * @return
	 */
	List<BatchJobExecution> getLastJobExecutions(Integer numResults, List<String> applicationJobs);
	
	/**
	 * @param jobInstanceId
	 * @return
	 */
	BatchJobExecution getJobExecution(Long jobInstanceId);

	/**
	 * @param numExecutions
	 * @return
	 */
	List<BatchJobExecution> getLastJobExecutions(Integer numExecutions);
}