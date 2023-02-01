package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.bounceme.chronos.chguadalquivir.listener.ImportJobListener;
import net.bounceme.chronos.chguadalquivir.listener.LastExecutionsListener;
import net.bounceme.chronos.chguadalquivir.listener.StatExecutionsListener;
import net.bounceme.chronos.chguadalquivir.listener.TimeStepListener;

@SpringBootTest
public class TestListeners {
	
	@Autowired
	private LastExecutionsListener lastExecutionsListener;
	
	@Autowired
	private ImportJobListener importJobListener;
	
	@Autowired
	private StatExecutionsListener statExecutionsListener;
	
	@Autowired
	private TimeStepListener timeStepListener;

	@Test
	public void testLastExecutionsListener() {
		JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution();
		lastExecutionsListener.beforeJob(jobExecution);
		lastExecutionsListener.afterJob(jobExecution);
		
		assertTrue(true);
	}
	
	@Test
	public void testImportJobListener() {
		JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution();
		
		importJobListener.beforeJob(jobExecution);
		importJobListener.afterJob(jobExecution);
		
		assertTrue(true);
	}
	
	@Test
	public void testStatExecutionsListener() {
		JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution();
		statExecutionsListener.beforeJob(jobExecution);
		statExecutionsListener.afterJob(jobExecution);
		
		assertTrue(true);
	}
	
	@Test
	public void testTimeStepListener() {
		JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution();
		jobExecution.getExecutionContext().put("STEP_TIMES", new HashMap<String, Long>());
		
		StepExecution stepExecution = new StepExecution("step", jobExecution);
		timeStepListener.beforeStep(stepExecution);
		ExitStatus status = timeStepListener.afterStep(stepExecution);
		
		assertEquals(ExitStatus.COMPLETED, status);
	}
}
