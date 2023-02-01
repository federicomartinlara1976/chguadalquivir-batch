package net.bounceme.chronos.chguadalquivir.tasklet;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;
import net.bounceme.chronos.chguadalquivir.services.ExecutionStatsService;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.chguadalquivir.support.StatsCalculations;

/**
 * @author fxm105
 *
 */
@Component
@Slf4j
public class SummarizeStatsExecutionTasklet implements Tasklet {

	@Autowired
	private CHGuadalquivirHelper helper;

	@Autowired
	private StatsCalculations stats;

	@Autowired
	private ExecutionStatsService executionStatsService;

	@SuppressWarnings({ "unchecked" })
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<Execution> executions = (List<Execution>) chunkContext.getStepContext().getJobExecutionContext()
				.get("EXECUTIONS");

		Double average = helper.round(stats.calculateAverage(executions), 2);
		Double deviation = helper.round(stats.calculateDeviation(executions, average), 2);
		Double variation = helper.round(stats.calculateVariation(deviation), 2);
		
		ExecutionStats executionStats = ExecutionStats.builder().initDate(new Date()).average(average)
				.deviation(deviation).variation(variation).build();

		executionStatsService.save(executionStats);
		log.info("{}", executionStats);

		return RepeatStatus.FINISHED;
	}
}
