package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import org.springframework.batch.core.JobInstance;

import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;
import net.bounceme.chronos.chguadalquivir.model.ExecutionResult;

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
	 * @return
	 */
	BatchJobExecution getLastJob();
	
	/**
	 * @param numJobs
	 * @return
	 */
	List<BatchJobExecution> getLastJobs(Integer numJobs);
	
	/**
	 * @return
	 */
	List<String> getJobNames();
	
	/**
	 * @param name
	 * @return
	 */
	String getJobScheduling(String name);
}
