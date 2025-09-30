package net.bounceme.chronos.chguadalquivir.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import net.bounceme.chronos.chguadalquivir.model.Execution;

@Component
public class StatExecutionsListener extends AbstractListener {
	
	/**
	 *
	 */
	protected void initializeConfig(JobExecution jobExecution) {
		Assert.notNull(jobExecution, "La ejecución no debe ser nula");
		
		List<Execution> executions = new ArrayList<>();
		jobExecution.getExecutionContext().put("EXECUTIONS", executions);
	}

	@Override
	protected void updateStatus(JobExecution jobExecution) {
		Assert.notNull(jobExecution, "La ejecución no debe ser nula");
		jobExecution.setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
	}

}
