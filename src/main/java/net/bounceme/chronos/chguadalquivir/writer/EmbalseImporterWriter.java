package net.bounceme.chronos.chguadalquivir.writer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.dto.chguadalquivir.CHGuadalquivirMessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.EmbalseDTO;
import net.bounceme.chronos.dto.chguadalquivir.MessageType;

@Component
@Slf4j
public class EmbalseImporterWriter implements ItemWriter<Embalse> {
	
	@Value("${application.queue}")
	private String queueName;
	
	private RabbitTemplate rabbitTemplate;
	
	private CHGuadalquivirHelper helper;

	public EmbalseImporterWriter(RabbitTemplate rabbitTemplate, CHGuadalquivirHelper helper) {
		this.rabbitTemplate = rabbitTemplate;
		this.helper = helper;
	}

	@Override
    public synchronized void write(Chunk<? extends Embalse> items) throws Exception {
        for (Embalse embalse : items) {
        	
        	EmbalseDTO embalseDTO = EmbalseDTO.builder()
        			.codigo(embalse.getId())
        			.embalse(embalse.getNombre())
        			.capacidad(embalse.getCapacidad())
        			.men(embalse.getMen())
        			.build();
    		
        	CHGuadalquivirMessageDTO<EmbalseDTO> messageDTO = helper.buidMessage(embalseDTO,
        			EmbalseDTO.class, MessageType.EMBALSE);
    		
    		rabbitTemplate.convertAndSend(queueName, messageDTO);
    		log.info("Writed {}", embalseDTO.toString());
        }
    }

}
