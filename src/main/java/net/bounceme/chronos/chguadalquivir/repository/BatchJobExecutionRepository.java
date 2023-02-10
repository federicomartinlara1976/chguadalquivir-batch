package net.bounceme.chronos.chguadalquivir.repository;

import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;

public interface BatchJobExecutionRepository {

	/**
	 * @return
	 */
	BatchJobExecution getLastJobExecution();
}
