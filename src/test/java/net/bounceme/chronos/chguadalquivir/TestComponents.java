package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;
import net.bounceme.chronos.chguadalquivir.repository.ExecutionStatsRepository;
import net.bounceme.chronos.chguadalquivir.repository.impl.RepositoryCollectionCustomImpl;
import net.bounceme.chronos.chguadalquivir.services.ExecutionStatsService;
import net.bounceme.chronos.chguadalquivir.support.StatsCalculations;

@SpringBootTest
public class TestComponents {
	
	@Autowired
	private StatsCalculations statsCalculations;
	
	@Autowired
	private RepositoryCollectionCustomImpl repositoryCollectionCustomImpl;
	
	@Autowired
	private ExecutionStatsService executionStatsService;
	
	@MockBean
	private ExecutionStatsRepository executionStatsRepository;

	@Test
	public void testStatCalculations() {
		List<Execution> executions = buildExecutions();
		
		Double average = statsCalculations.calculateAverage(executions);
		Double deviation = statsCalculations.calculateDeviation(executions, average);
		Double variation = statsCalculations.calculateVariation(deviation);
		
		assertNotNull(average);
		assertNotNull(deviation);
		assertNotNull(variation);
	}
	
	@Test
	public void testExecutionStatsService() {
		ExecutionStats executionStats = ExecutionStats.builder().average(1.0).deviation(1.0).variation(1.0).build();
		
		Mockito.doReturn(executionStats).when(executionStatsRepository).save(Mockito.isA(ExecutionStats.class));
		
		executionStatsService.save(executionStats);
	}
	
	@Test
	public void testRepositoryCollectionCustomImpl() {
		repositoryCollectionCustomImpl.setCollectionName("collection");
		assertEquals("collection", repositoryCollectionCustomImpl.getCollectionName());
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
