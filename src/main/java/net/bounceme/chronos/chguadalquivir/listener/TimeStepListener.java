package net.bounceme.chronos.chguadalquivir.listener;

import java.util.Map;
import java.util.Objects;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimeStepListener implements StepExecutionListener {

	private Long startTime;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("STEPLISTENER: Se va a ejecutar el step con nombre: {}", stepExecution.getStepName());
		startTime = System.currentTimeMillis();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		Long duration = System.currentTimeMillis() - startTime;
		log.info("STEPLISTENER: Se ha terminado de ejecutar el step con nombre: {}, tiempo transcurrido {} ms",
				stepExecution.getStepName(), duration);
		
		Map<String, Long> stepTimes = (Map<String, Long>) stepExecution.getJobExecution().getExecutionContext().get("STEP_TIMES");
		if (!Objects.isNull(stepTimes))
			stepTimes.put(stepExecution.getStepName(), duration);
		
		return ExitStatus.COMPLETED;
	}

}
