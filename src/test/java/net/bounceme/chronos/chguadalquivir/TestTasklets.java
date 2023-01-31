package net.bounceme.chronos.chguadalquivir;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;
import net.bounceme.chronos.chguadalquivir.services.ExecutionStatsService;
import net.bounceme.chronos.chguadalquivir.tasklet.SummarizeStatsExecutionTasklet;

@SpringBootTest
public class TestTasklets {
	
	@Autowired
	private SummarizeStatsExecutionTasklet summarizeStatsExecutionTasklet;
	
	@MockBean
	private ExecutionStatsService executionStatsService;
	
	@Value("${application.importJob.url}")
	private String url;
	
	@Test
	public void testSummarizeStatsExecutionTasklet() {
		try {
			List<Execution> executions = buildExecutions();
			
			JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution();
			jobExecution.getExecutionContext().put("EXECUTIONS", executions);
			
			StepContext stepContext = new StepContext(new StepExecution("summarizeStatsStep", jobExecution));
			ChunkContext chunkContext = new ChunkContext(stepContext);
			
			Mockito.doNothing().when(executionStatsService).save(Mockito.isA(ExecutionStats.class));
			
			RepeatStatus status = summarizeStatsExecutionTasklet.execute(null, chunkContext);
			assertEquals(RepeatStatus.FINISHED, status);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	private List<Execution> buildExecutions() {
		List<Execution> executions = new ArrayList<>();
		
		Execution execution = Execution.builder().id("2023-01-23").value(1).executionTime(1896L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-24").value(1).executionTime(1923L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-25").value(1).executionTime(1642L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-26").value(1).executionTime(1828L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-27").value(1).executionTime(1805L).build();
		executions.add(execution);
		
		execution = Execution.builder().id("2023-01-28").value(1).executionTime(1665L).build();
		executions.add(execution);
		
		return executions;
	}
}
