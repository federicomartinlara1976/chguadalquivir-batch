package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import org.springframework.batch.core.JobInstance;

import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;

public interface JobService {
	
	/**
	 * @param name
	 * @throws Exception
	 */
	void run(String name) throws Exception;
	
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
	 * @return
	 */
	List<String> getJobNames();
	
	/**
	 * @param name
	 * @return
	 */
	String getJobScheduling(String name);
}
