package net.bounceme.chronos.chguadalquivir.tasklet;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;

/**
 * @author fxm105
 *
 */
@Component
@Slf4j
public class SummarizeStatsExecutionTasklet implements Tasklet {
	
	@Autowired
	private CHGuadalquivirHelper helper;

	@SuppressWarnings({ "unchecked" })
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<Execution> executions = (List<Execution>) chunkContext.getStepContext().getJobExecutionContext().get("EXECUTIONS");
		
		Double average = calculateAvg(executions);
		log.info("Average: {}", helper.round(average, 2));
		
		return RepeatStatus.FINISHED;
	}

	/**
	 * @param executions
	 * @return
	 */
	private Double calculateAvg(List<Execution> executions) {
		Double average = 0.0;
		for (Execution execution : executions) {
			average += execution.getExecutionTime();
		}
		
		return average / executions.size();
	}
}
