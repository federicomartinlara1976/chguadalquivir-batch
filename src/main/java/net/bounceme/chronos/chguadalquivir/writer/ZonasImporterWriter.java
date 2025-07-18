package net.bounceme.chronos.chguadalquivir.writer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.dto.chguadalquivir.CHGuadalquivirMessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.MessageType;
import net.bounceme.chronos.dto.chguadalquivir.ZonaDTO;

@Component
@Slf4j
public class ZonasImporterWriter implements ItemWriter<Zona> {
	
	@Value("${application.queue}")
	private String queueName;
	
	private RabbitTemplate rabbitTemplate;
	
	private CHGuadalquivirHelper helper;

    public ZonasImporterWriter(RabbitTemplate rabbitTemplate, CHGuadalquivirHelper helper) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.helper = helper;
	}

	@Override
    public synchronized void write(Chunk<? extends Zona> items) throws Exception {
        for (Zona zona : items) {
        	ZonaDTO zonaDTO = ZonaDTO.builder()
        			.codigo(zona.getCodigo())
        			.nombre(zona.getNombre())
        			.descripcion(zona.getDescripcion())
        			.build();
    		
        	CHGuadalquivirMessageDTO<ZonaDTO> messageDTO = helper.buidMessage(zonaDTO,
        			ZonaDTO.class, MessageType.ZONA);
    		
    		rabbitTemplate.convertAndSend(queueName, messageDTO);
    		log.info("Writed {}", zonaDTO.toString());
        }
    }

}
