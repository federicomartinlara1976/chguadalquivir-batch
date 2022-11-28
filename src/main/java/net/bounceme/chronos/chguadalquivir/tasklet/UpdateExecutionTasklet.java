package net.bounceme.chronos.chguadalquivir.tasklet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.repository.ExecutionsRepository;

/**
 * @author fxm105
 *
 */
@Component
@Slf4j
public class UpdateExecutionTasklet implements Tasklet {
	
	@Autowired
	private ExecutionsRepository executionsRepository;
	
	@Autowired
	private SimpleDateFormat dateFormat;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Date date = new Date();
		
		List<Execution> executions = executionsRepository.findByDate(dateFormat.format(date));
		Execution execution = null;
		
		if (CollectionUtils.isEmpty(executions)) {
        	execution = new Execution();
        	execution.setId(dateFormat.format(date));
        	execution.setValue(1);
        }
		else {
			execution = executions.get(0);
			Integer value = execution.getValue() + 1;
			execution.setValue(value);
		}
		
		executionsRepository.save(execution);
		
		log.info("Execution count updated");
		return RepeatStatus.FINISHED;
	}
}
