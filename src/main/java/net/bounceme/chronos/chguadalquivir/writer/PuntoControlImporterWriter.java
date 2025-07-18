package net.bounceme.chronos.chguadalquivir.writer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.PuntoControl;
import net.bounceme.chronos.chguadalquivir.repository.PuntoControlRepository;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.dto.chguadalquivir.CHGuadalquivirMessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.MessageType;
import net.bounceme.chronos.dto.chguadalquivir.PuntoControlDTO;

@Component
@Slf4j
public class PuntoControlImporterWriter implements ItemWriter<PuntoControl> {
	
	@Value("${application.queue}")
	private String queueName;
	
	private PuntoControlRepository  repository;
	
	private RabbitTemplate rabbitTemplate;
	
	private CHGuadalquivirHelper helper;

	public PuntoControlImporterWriter(PuntoControlRepository repository, RabbitTemplate rabbitTemplate,
			CHGuadalquivirHelper helper) {
		super();
		this.repository = repository;
		this.rabbitTemplate = rabbitTemplate;
		this.helper = helper;
	}

	@Override
    public synchronized void write(Chunk<? extends PuntoControl> items) throws Exception {
        for (PuntoControl punto : items) {
        	
        	repository.save(punto);
        	
        	PuntoControlDTO puntoControlDTO = PuntoControlDTO.builder()
        			.id(punto.getId())
        			.nombre(punto.getNombre())
        			.zona(punto.getZona())
        			.build();
    		
        	CHGuadalquivirMessageDTO<PuntoControlDTO> messageDTO = helper.buidMessage(puntoControlDTO,
        			PuntoControlDTO.class, MessageType.PUNTO_CONTROL);
    		
    		rabbitTemplate.convertAndSend(queueName, messageDTO);
    		log.info("Writed {}", puntoControlDTO.toString());
        }
    }

}
