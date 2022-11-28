package net.bounceme.chronos.chguadalquivir.flow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.repository.ExecutionsRepository;

/**
 * @author federico
 *
 */
@Component
@Slf4j
public class IsExecutedDecider implements JobExecutionDecider {

	@Autowired
	private ExecutionsRepository executionsRepository;

	@Autowired
	private SimpleDateFormat dateFormat;

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		Date date = new Date();
		String fecha = dateFormat.format(date);
		List<Execution> executions = executionsRepository.findByDate(fecha);
		
		String isExecuted = "NO_EXECUTED";
		if (CollectionUtils.isEmpty(executions)) {
			log.info("Ya se ha ejecutado para la fecha {}", fecha);
			isExecuted = "EXECUTED";
		}

		return new FlowExecutionStatus(isExecuted);
	}
}
