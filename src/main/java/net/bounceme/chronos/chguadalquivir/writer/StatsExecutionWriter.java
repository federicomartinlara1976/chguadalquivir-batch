package net.bounceme.chronos.chguadalquivir.writer;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Execution;

@Component
@StepScope
@Slf4j
public class StatsExecutionWriter implements ItemWriter<Execution> {

	private JobExecution jobExecution;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends Execution> items) throws Exception {
		List<Execution> executions = (List<Execution>) jobExecution.getExecutionContext().get("EXECUTIONS");

		for (Execution execution : items) {
			log.info("{}", execution.toString());
			executions.add(execution);
		}
	}
}
