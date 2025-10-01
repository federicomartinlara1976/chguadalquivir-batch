package net.bounceme.chronos.chguadalquivir.listener;

import java.util.Map;
import java.util.Objects;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimeStepListener implements StepExecutionListener {

	private Long startTime;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		Assert.notNull(stepExecution, "La ejecución del paso no puede ser null");
		
		log.info("STEPLISTENER: Se va a ejecutar el step con nombre: {}", stepExecution.getStepName());
		startTime = System.currentTimeMillis();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		Assert.notNull(stepExecution, "La ejecución del paso no puede ser null");
		Assert.notNull(startTime, "startTime = null");
		
		Long duration = System.currentTimeMillis() - startTime;
		log.info("STEPLISTENER: Se ha terminado de ejecutar el step con nombre: {}, ha tardado {} ms",
				stepExecution.getStepName(), duration);
			
		Map<String, Long> stepTimes = (Map<String, Long>) stepExecution.getJobExecution().getExecutionContext().get("STEP_TIMES");
		if (!Objects.isNull(stepTimes))
			stepTimes.put(stepExecution.getStepName(), duration);
			
		return ExitStatus.COMPLETED;
	}

}
