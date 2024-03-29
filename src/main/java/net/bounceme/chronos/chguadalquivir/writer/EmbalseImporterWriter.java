package net.bounceme.chronos.chguadalquivir.writer;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.dto.chguadalquivir.EmbalseDTO;
import net.bounceme.chronos.dto.chguadalquivir.MessageDTO;

@Component
@Slf4j
public class EmbalseImporterWriter implements ItemWriter<Embalse> {
	
	@Value("${application.queue}")
	private String queueName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

    @SuppressWarnings("rawtypes")
	@Override
    public synchronized void write(List<? extends Embalse> items) throws Exception {
        for (Embalse embalse : items) {
        	
        	EmbalseDTO embalseDTO = EmbalseDTO.builder()
        			.codigo(embalse.getId())
        			.embalse(embalse.getNombre())
        			.build();
    		
			MessageDTO messageDTO = MessageDTO.builder()
    				.className(EmbalseDTO.class.getName())
    				.data(embalseDTO)
    				.build();
    		
    		rabbitTemplate.convertAndSend(queueName, messageDTO);
    		log.info("Writed {}", embalseDTO.toString());
        }
    }

}
