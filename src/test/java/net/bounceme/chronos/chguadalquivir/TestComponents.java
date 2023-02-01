package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.support.StatsCalculations;

@SpringBootTest
public class TestComponents {
	
	@Autowired
	private StatsCalculations statsCalculations;

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
