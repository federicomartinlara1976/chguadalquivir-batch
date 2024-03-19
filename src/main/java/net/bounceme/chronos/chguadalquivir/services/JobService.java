package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import org.springframework.batch.core.JobInstance;

import net.bounceme.chronos.chguadalquivir.model.ExecutionResult;
import net.bounceme.chronos.jpa.model.batch.BatchJobExecution;

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
}
