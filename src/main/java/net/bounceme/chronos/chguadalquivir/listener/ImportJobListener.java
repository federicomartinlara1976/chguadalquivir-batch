package net.bounceme.chronos.chguadalquivir.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.dto.NotificacionDTO;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.dto.chguadalquivir.MessageDTO;

@Component
@Slf4j
public class ImportJobListener extends AbstractListener {
	
	@Value("${application.notification.queue}")
	private String queueName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private CHGuadalquivirHelper helper;
	
	/**
	 *
	 */
	protected void initializeConfig(JobExecution jobExecution) {
		Map<String, Long> stepTimes = new HashMap<>();
		
		jobExecution.getExecutionContext().put("STEP_TIMES", stepTimes);
	}

	@Override
	protected void updateStatus(JobExecution jobExecution) {
		Boolean alreadyExecuted = (Boolean) jobExecution.getExecutionContext().get("ALREADY_EXECUTED");
		
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			if (!Objects.isNull(alreadyExecuted) && Boolean.TRUE.equals(alreadyExecuted)) {
				log.error("La tarea ya ha sido ejecutada");
				jobExecution.setExitStatus(new ExitStatus("NOOP", "La tarea ya ha sido ejecutada"));
				
				sendNotificacion("La tarea ya ha sido ejecutada");
			}
		}
		else {
			jobExecution.setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
			sendNotificacion("La tarea ha sido ejecutada correctamente");
		}
	}

	@SuppressWarnings("rawtypes")
	private void sendNotificacion(String mensaje) {
		NotificacionDTO notificacion = NotificacionDTO.builder()
				.aplicacion("chguadalquivir-batch")
				.timestamp(System.currentTimeMillis())
				.mensaje(mensaje)
				.build();
		
		MessageDTO message = helper.createNotificacion(notificacion);
		
		rabbitTemplate.convertAndSend(queueName, message);
	}
}
