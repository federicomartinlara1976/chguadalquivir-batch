package net.bounceme.chronos.chguadalquivir;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.bounceme.chronos.chguadalquivir.listener.ImportJobListener;
import net.bounceme.chronos.chguadalquivir.listener.LastExecutionsListener;
import net.bounceme.chronos.chguadalquivir.listener.StatExecutionsListener;

@SpringBootTest
public class TestListeners {
	
	@Autowired
	private LastExecutionsListener lastExecutionsListener;
	
	@Autowired
	private ImportJobListener importJobListener;
	
	@Autowired
	private StatExecutionsListener statExecutionsListener;

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
}
