package net.bounceme.chronos.chguadalquivir.services;

import org.springframework.batch.core.JobInstance;

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
}
