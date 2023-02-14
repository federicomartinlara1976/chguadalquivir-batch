package net.bounceme.chronos.chguadalquivir.repository;

import java.util.List;

import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;

public interface BatchJobExecutionRepository {

	/**
	 * @return
	 */
	BatchJobExecution getLastJobExecution();
	
	/**
	 * @param numResults
	 * @return
	 */
	List<BatchJobExecution> getLastJobExecutions(Integer numResults);
}