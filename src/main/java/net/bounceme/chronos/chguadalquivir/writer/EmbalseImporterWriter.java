package net.bounceme.chronos.chguadalquivir.writer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.dto.chguadalquivir.CHGuadalquivirMessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.EmbalseDTO;
import net.bounceme.chronos.dto.chguadalquivir.MessageType;

@Component
@Slf4j
public class EmbalseImporterWriter implements ItemWriter<Embalse> {
	
	@Value("${application.queue}")
	private String queueName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

    @Override
    public synchronized void write(Chunk<? extends Embalse> items) throws Exception {
        for (Embalse embalse : items) {
        	
        	String codigoZona = embalse.getId().substring(0, 2);
        	
        	EmbalseDTO embalseDTO = EmbalseDTO.builder()
        			.codigo(embalse.getId())
        			.nombreEmbalse(embalse.getNombre())
        			.capacidad(embalse.getCapacidad())
        			.men(embalse.getMen())
        			.codigoZona(codigoZona)
        			.build();
    		
        	CHGuadalquivirMessageDTO<EmbalseDTO> messageDTO = new CHGuadalquivirMessageDTO<>();
        	messageDTO.setMessageType(MessageType.EMBALSE);
        	messageDTO.setData(embalseDTO);
    		
    		rabbitTemplate.convertAndSend(queueName, messageDTO);
    		log.info("Writed {}", embalseDTO.toString());
        }
    }

}
