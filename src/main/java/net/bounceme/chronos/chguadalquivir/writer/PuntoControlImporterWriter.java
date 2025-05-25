package net.bounceme.chronos.chguadalquivir.writer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.PuntoControl;
import net.bounceme.chronos.chguadalquivir.repository.PuntoControlRepository;
import net.bounceme.chronos.dto.MessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.PuntoControlDTO;

@Component
@Slf4j
public class PuntoControlImporterWriter implements ItemWriter<PuntoControl> {
	
	@Autowired
	private PuntoControlRepository  repository;
	
	@Value("${application.queue}")
	private String queueName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

    @SuppressWarnings("rawtypes")
	@Override
    public synchronized void write(Chunk<? extends PuntoControl> items) throws Exception {
        for (PuntoControl punto : items) {
        	
        	repository.save(punto);
        	
        	PuntoControlDTO puntoControlDTO = PuntoControlDTO.builder()
        			.id(punto.getId())
        			.nombre(punto.getNombre())
        			.zona(punto.getZona())
        			.build();
    		
			MessageDTO messageDTO = MessageDTO.builder()
    				.className(PuntoControlDTO.class.getName())
    				.data(puntoControlDTO)
    				.build();
    		
    		rabbitTemplate.convertAndSend(queueName, messageDTO);
    		log.info("Writed {}", puntoControlDTO.toString());
        }
    }

}
